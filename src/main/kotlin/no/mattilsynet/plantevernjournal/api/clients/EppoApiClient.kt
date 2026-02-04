package no.mattilsynet.plantevernjournal.api.clients

import kotlinx.coroutines.reactive.awaitSingle
import no.mattilsynet.plantevernjournal.api.clients.models.EppoTaxon
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.util.retry.Retry
import java.time.Duration

@Component
class EppoApiClient(
    @Value("\${eppo.api.token}") private val eppoToken: String,
    @Value("\${eppo.api.uri}") private val eppoUri: String,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Suppress("MagicNumber")
    private val retrySpec = Retry.fixedDelay(3, Duration.ofSeconds(2))

    suspend fun getNavnFraEppoKode(eppoKode: String) =
        runCatching {
            WebClient.create()
                .get()
                .uri("$eppoUri/taxons/taxon/${eppoKode}/names")
                .header("X-Api-Key", eppoToken)
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux<EppoTaxon>()
                .retryWhen(retrySpec)
                .collectList()
                .awaitSingle()
        }.onFailure {
            logger.warn("getNavnFraEppokode fikk feil for eppokode $eppoKode", it)
        }.getOrNull().also {
            logger.info("Henter koden $eppoKode fra eppo")
        }

}