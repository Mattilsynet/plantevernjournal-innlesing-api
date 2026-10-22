package no.mattilsynet.plantevernjournal.api.services

import no.mattilsynet.plantevernjournal.api.clients.EppoApiClient
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoNats
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoApiClient: EppoApiClient,
    private val eppoKvConsumer: EppoKvConsumer,
) {
    suspend fun getNavnFraEppoKode(eppoKode: String) =
        eppoKvConsumer.getEppoNavnFraNats(eppoKode = eppoKode)
            ?: hentGyldigEppokodeFraEppo(eppoKode)

    private suspend fun hentGyldigEppokodeFraEppo(eppoKode: String): String? =
        eppoApiClient.getNavnFraEppoKode(eppoKode = eppoKode)
            ?.maxByOrNull { it.level }
            ?.also { eppoTaxon ->
                require(eppoTaxon.type == "Not Specified" || eppoTaxon.level >= GENUS_LEVEL) {
                    "Koden $eppoKode har nivå ${eppoTaxon.level} (${eppoTaxon.type}), som er lavere enn 7 (Genus)"
                }
                // Man skal kunne melde inn avlinger, dvs alt under koden 3CRGK.
                // Disse har type Not Specified, starter med 3 og slutter på C
                require(
                    eppoTaxon.type != "Not Specified" ||
                            (eppoTaxon.eppocode.startsWith("3") &&
                                    eppoTaxon.eppocode.endsWith("C"))
                ) {
                    "Koden $eppoKode er ikke gyldig for innmelding. Det er kun koder for Crop groups som kan sendes inn"
                }
            }
            ?.prefname
            ?.also { eppoNavn ->
                eppoKvConsumer.putEppoTilNats(eppoNats = EppoNats(eppoKode = eppoKode, eppoNavn = eppoNavn))
            }

    companion object {
        const val GENUS_LEVEL = 7
    }
}
