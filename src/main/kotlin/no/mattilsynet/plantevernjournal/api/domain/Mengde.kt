package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Enhet

@Serializable

data class Mengde (
    val enhet: Enhet,

    val verdi: Double,
)