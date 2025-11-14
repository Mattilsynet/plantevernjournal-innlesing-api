package no.mattilsynet.plantevernjournal.api.services

import kotlinx.coroutines.runBlocking
import no.mattilsynet.plantevernjournal.api.clients.EppoClient
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppokodeNats
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoClient: EppoClient,
    private val eppoKvConsumer: EppoKvConsumer,
) {
    fun getNavnFraEppokode(eppokode: String) =
        runBlocking {
            eppoKvConsumer.getEppokode(eppokode = eppokode)
                ?: eppoClient.getNavnFraEppokode(eppokode = eppokode)
                    ?.split(";")
                    ?.let {
                        EppokodeNats(it[0], it[1])
                    }?.also {
                        eppoKvConsumer.putEppokode(it)
                    }
        }

}
