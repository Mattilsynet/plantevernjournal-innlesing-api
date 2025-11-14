package no.mattilsynet.plantevernjournal.api.nats.consumers.models

import kotlinx.serialization.Serializable

@Serializable
data class EppokodeNats(
    val eppokode: String,
    val eppoNavn: String,
)
