package no.mattilsynet.plantevernjournal.api.services

import no.mattilsynet.plantevernjournal.api.clients.EppoApiClient
import no.mattilsynet.plantevernjournal.api.clients.models.EppoTaxon
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoNats
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoApiClient: EppoApiClient,
    private val eppoKvConsumer: EppoKvConsumer,
) {
    suspend fun getNavnFraEppoKode(eppoKode: String) =
        eppoKvConsumer.getEppoFraNats(eppoKode = eppoKode)
            ?: eppoApiClient.getNavnFraEppoKode(eppoKode = eppoKode)
        ?.getPrioritertNavn()?.fullname
        ?.also { eppoNavn ->
            eppoKvConsumer.putEppoTilNats(eppoNats = EppoNats(eppoKode = eppoKode, eppoNavn = eppoNavn))
        }

    private fun List<EppoTaxon>.getPrioritertNavn() =
        firstOrNull { it.preferred } ?: firstOrNull { it.landIso == "en" } ?: firstOrNull()
}
