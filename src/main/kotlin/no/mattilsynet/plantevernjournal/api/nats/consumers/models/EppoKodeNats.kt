package no.mattilsynet.plantevernjournal.api.nats.consumers.models

import kotlinx.serialization.Serializable

@Serializable
data class EppoKodeNats(
    val eppoKode: String,
    val eppoNavn: String,
)
