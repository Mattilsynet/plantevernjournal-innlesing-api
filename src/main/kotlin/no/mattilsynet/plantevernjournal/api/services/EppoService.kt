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
                    ?.splitEppoString()
                    ?.let { eppoPair ->
                        EppokodeNats(
                            eppokode = eppoPair[0],
                            eppoNavn = eppoPair[1]
                        ).also { eppokodeNats ->
                            eppoKvConsumer.putEppokode( eppokodeNats = eppokodeNats)
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
