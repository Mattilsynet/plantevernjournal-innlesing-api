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
                require(eppoTaxon.type != "Not Specified" && eppoTaxon.level >= GENUS_LEVEL) {
                    "Eppokoden $eppoKode har nivå ${eppoTaxon.level} (${eppoTaxon.type}), som er lavere enn 7 (Genus)"
                }
                // Man skal kunne melde inn gressavlinger, fx 3GRAC. Disse har type Not Specified
                require(
                    eppoTaxon.type != "Not Specified" ||
                            eppoTaxon.eppocode.startsWith("3")
                ) {
                    "Eppokoden $eppoKode er ikke gyldig for innmelding. Må starte med 3"
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
