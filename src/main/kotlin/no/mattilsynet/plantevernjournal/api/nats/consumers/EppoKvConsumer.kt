package no.mattilsynet.plantevernjournal.api.nats.consumers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.mattilsynet.fisk.libs.virtualnats.VirtualNats
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppokodeNats
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EppoKvConsumer(
    private val nats: VirtualNats,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Putter eppokoder til nats
     * @param eppokodeNats som sendes inn sammen med journaldata
     */
    fun putEppokode(eppokodeNats: EppokodeNats) =
        runCatching {
            nats.keyValue(
                bucketName = "eppo_kode_v1",
            ).put(
                key = eppokodeNats.eppokode,
                value = Json.encodeToString(eppokodeNats).toByteArray(),
            )
        }.onFailure {
            logger.warn(
                "putEppoKode feiler for eppokode: ${eppokodeNats.eppokode} og kodestring ${eppokodeNats.eppoNavn}",
                it
            )
        }.getOrNull()

    /**
     * Henter eppokoder fra nats
     * @param eppokode som sendes inn sammen med journaldata
     * @return String
     */
    fun getEppokode(eppokode: String): String? =
        runCatching {
            nats.keyValue(
                "eppo_kode_v1",
            ).get(key = eppokode)
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
            logger.warn("getEppoKode feiler for eppokode: $eppokode", it)
        }.getOrNull()

}
