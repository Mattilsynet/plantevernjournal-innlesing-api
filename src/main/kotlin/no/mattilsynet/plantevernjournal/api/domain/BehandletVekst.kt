package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable

@Serializable
data class BehandletVekst(
    val bbchFase: String?,

    val eppoKode: String,

    val eppoNavn: String,

    val partinummer: Int?,

    val sort: String?,
)
