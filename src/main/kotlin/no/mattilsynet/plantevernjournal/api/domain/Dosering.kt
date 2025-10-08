package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.Enhet

@Serializable
data class Dosering(
    val enhet: Enhet,

    val verdi: Double,
)
