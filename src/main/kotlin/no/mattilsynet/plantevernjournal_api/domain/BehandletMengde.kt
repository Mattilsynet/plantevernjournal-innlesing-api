package no.mattilsynet.plantevernjournal_api.domain

import no.mattilsynet.plantevernjournal_api.shared.Enhet

data class BehandletMengde(
    val verdi: Float,
    val enhet: Enhet
) {
}