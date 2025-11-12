package no.mattilsynet.plantevernjournal.api.nats.consumers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.mattilsynet.fisk.libs.virtualnats.VirtualNats
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoKodeNats
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EppoKvConsumer(
    private val nats: VirtualNats,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Putter eppokoder til nats
     * @param eppoKodeNats som sendes inn sammen med journaldata
     */
    fun putEppoKode(eppoKodeNats: EppoKodeNats) =
        runCatching {
            nats.keyValue(
                bucketName = "eppo_kode_v1",
            ).put(
                key = eppoKodeNats.eppoKode,
                value = Json.encodeToString(eppoKodeNats).toByteArray(),
            )
        }.onFailure {
            logger.warn(
                "putEppoKode feiler for eppokode: ${eppoKodeNats.eppoKode} og kodestring ${eppoKodeNats.eppoNavn}",
                it
            )
        }.getOrNull()

    /**
     * Henter eppokoder fra nats
     * @param eppoKode som sendes inn sammen med journaldata
     * @return String
     */
    fun getEppoKode(eppoKode: String): String? =
        runCatching {
            nats.keyValue(
                "eppo_kode_v1",
            ).get(key = eppoKode)
                ?.let { eppokode ->
                    runCatching {
                        eppokode.getValue()
                            ?.let { String(it) }
                    }.onFailure { throwable ->
                        logger.error(
                            "Kunne ikke parse $eppokode som string",
                            throwable
                        )
                    }.getOrNull()
                }
        }.onFailure {
            logger.warn("getEppoKode feiler for eppokode: $eppoKode", it)
        }.getOrNull()

}
