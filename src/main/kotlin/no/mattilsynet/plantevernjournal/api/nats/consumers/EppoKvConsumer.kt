package no.mattilsynet.plantevernjournal.api.nats.consumers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.mattilsynet.fisk.libs.virtualnats.VirtualNats
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoNats
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EppoKvConsumer(
    private val nats: VirtualNats,
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Putter eppokode med beskrivelse til nats
     * @param eppoNats som sendes inn sammen med journaldata
     */
    fun putEppoTilNats(eppoNats: EppoNats) =
        runCatching {
            nats.keyValue(
                bucketName = "eppo_kode_v1",
            ).put(
                key = eppoNats.eppoKode,
                value = Json.encodeToString(eppoNats).toByteArray(),
            )
        }.onFailure {
            logger.warn(
                "putEppoTilNats feiler for eppokode: ${eppoNats.eppoKode} og kodestring ${eppoNats.eppoNavn}",
                it
            )
        }.getOrNull()

    /**
     * Henter eppokode med beskrivelse fra nats
     * @param eppoKode som sendes inn sammen med journaldata
     * @return String
     */
    fun getEppoFraNats(eppoKode: String): String? =
        runCatching {
            nats.keyValue(
                "eppo_kode_v1",
            ).get(key = eppoKode)
                ?.let { eppoKode ->
                    runCatching {
                        eppoKode.getValue()
                            ?.let { String(it) }
                    }.onFailure { throwable ->
                        logger.error(
                            "Kunne ikke parse $eppoKode som string",
                            throwable
                        )
                    }.getOrNull()
                }
        }.onFailure {
            logger.warn("getEppoFraNats feiler for eppokode: $eppoKode", it)
        }.getOrNull()

}
