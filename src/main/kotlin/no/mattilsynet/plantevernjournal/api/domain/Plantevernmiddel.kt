package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class Plantevernmiddel(
    val autorisasjonsnummer: String,

    val dosering: Dosering,
)
