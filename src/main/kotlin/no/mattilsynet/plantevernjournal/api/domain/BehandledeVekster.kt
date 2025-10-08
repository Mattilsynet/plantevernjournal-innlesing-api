package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class BehandledeVekster(
    val bbchFase: String?,

    val eppoKode: String,

    val partinummer: Int?,
)
