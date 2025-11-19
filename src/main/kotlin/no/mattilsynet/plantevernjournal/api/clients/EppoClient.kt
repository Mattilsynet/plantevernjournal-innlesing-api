package no.mattilsynet.plantevernjournal.api.clients
import no.mattilsynet.plantevernjournal.api.clients.models.Code2PrefamesResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import java.time.Duration

@Component
class EppoClient(
    @Value("\${eppo.token}") private val eppoToken: String,
    @Value("\${eppo.uri}") private val eppoUri: String,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Suppress("MagicNumber")
    private val retrySpec = Retry.fixedDelay(3, Duration.ofSeconds(2))

    suspend fun getNavnFraEppoKode(eppoKode: String) =
        runCatching {
            WebClient.create()
                .post()
                .uri("$eppoUri/tools/codes2prefnames")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("authtoken=$eppoToken&intext=$eppoKode")
                .retrieve()
                .bodyToMono(Code2PrefamesResponse::class.java)
                .retryWhen(retrySpec)
                .block()
                ?.response
                .also {
                    logger.info("Henter koden $eppoKode fra eppo")
                }
        }.onFailure {
            logger.warn("getNavnFraEppokode fikk feil for eppokode $eppoKode", it)
            throw it
        }.getOrNull()
}
