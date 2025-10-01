package no.mattilsynet.plantevernjournal_api.domain

import no.mattilsynet.plantevernjournal_api.shared.Enhet

data class Dosering(
    val enhet: Enhet,
    val verdi: Float,
)