package no.mattilsynet.plantevernjournal.api.services

import kotlinx.coroutines.runBlocking
import no.mattilsynet.plantevernjournal.api.clients.EppoClient
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoNats
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoClient: EppoClient,
    private val eppoKvConsumer: EppoKvConsumer,
) {
    fun getNavnFraEppoKode(eppoKode: String) =
        runBlocking {
            eppoKvConsumer.getEppoFraNats(eppoKode = eppoKode)
                ?: eppoClient.getNavnFraEppoKode(eppoKode = eppoKode)
                    ?.splitEppoString()
                    ?.let { eppoPair ->
                        EppoNats(
                            eppoKode = eppoPair[0],
                            eppoNavn = eppoPair[1]
                        ).also { eppokodeNats ->
                            eppoKvConsumer.putEppoTilNats( eppoNats = eppokodeNats)
                        }
                    }?.eppoNavn
        }

    private fun String.splitEppoString() =
        split("|").firstNotNullOfOrNull { eppoString ->
            if (eppoString.isNotBlank() &&
                eppoString.contains(";") &&
                !eppoString.contains("NOT FOUND")
            ) {
                eppoString.split(";")
            } else null
        }

}
