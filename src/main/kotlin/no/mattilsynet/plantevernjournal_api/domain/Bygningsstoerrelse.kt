package no.mattilsynet.plantevernjournal_api.domain

import no.mattilsynet.plantevernjournal_api.shared.Enhet

data class Bygningsstoerrelse(
    val verdi: Float,
    val enhet: Enhet,
)