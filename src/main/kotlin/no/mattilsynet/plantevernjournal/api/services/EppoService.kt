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
        eppoKvConsumer.getEppoFraNats(eppoKode = eppoKode)
            ?: eppoApiClient.getNavnFraEppoKode(eppoKode = eppoKode)
                ?.maxBy { it.level }?.prefname
                ?.also { eppoNavn ->
                    eppoKvConsumer.putEppoTilNats(eppoNats = EppoNats(eppoKode = eppoKode, eppoNavn = eppoNavn))
                }
}
