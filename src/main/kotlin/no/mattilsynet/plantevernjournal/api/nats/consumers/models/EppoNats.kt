package no.mattilsynet.plantevernjournal.api.nats.consumers.models

import kotlinx.serialization.Serializable

@Serializable
data class EppoNats(
    val eppoKode: String,
    val eppoNavn: String,
)
