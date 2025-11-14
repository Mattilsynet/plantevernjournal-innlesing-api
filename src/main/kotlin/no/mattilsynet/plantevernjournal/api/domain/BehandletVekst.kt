package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class BehandletVekst(
    val bbchFase: String?,

    val eppokode: String,

    val partinummer: Int?,
)
