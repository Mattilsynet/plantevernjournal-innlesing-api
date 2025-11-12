package no.mattilsynet.plantevernjournal.api.services

import kotlinx.coroutines.runBlocking
import no.mattilsynet.plantevernjournal.api.clients.EppoClient
import org.springframework.stereotype.Service

@Service
class EppoService(
    private val eppoClient: EppoClient,
) {
    fun getNavnFraEppokode(eppokode: String) =
        runBlocking {
            eppoClient.getNavnFraEppokode(eppokode = eppokode)
        }
}
