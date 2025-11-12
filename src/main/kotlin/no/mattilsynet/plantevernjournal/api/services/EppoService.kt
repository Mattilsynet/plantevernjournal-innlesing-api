package no.mattilsynet.plantevernjournal.api.services

import kotlinx.coroutines.runBlocking
import no.mattilsynet.plantevernjournal.api.clients.EppoClient
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoKodeNats
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoClient: EppoClient,
    private val eppoKvConsumer: EppoKvConsumer,
) {
    fun getNavnFraEppokode(eppoKode: String) =
        runBlocking {
            eppoKvConsumer.getEppoKode(eppoKode = eppoKode)
                ?: eppoClient.getNavnFraEppoKode(eppoKode = eppoKode)
                    ?.split(";")
                    ?.let {
                        EppoKodeNats(it[0], it[1])
                    }?.also {
                        eppoKvConsumer.putEppoKode(it)
                    }
        }

}
