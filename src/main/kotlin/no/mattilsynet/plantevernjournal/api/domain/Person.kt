package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.controllers.models.PersonDto

@Serializable
data class Person (
    val etternavn: String,

    val fornavn: String,
) {
    fun toPersonDto() =
        PersonDto(
            etternavn = etternavn,
            fornavn = fornavn,
        )
}
