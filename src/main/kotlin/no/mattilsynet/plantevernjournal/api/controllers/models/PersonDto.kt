package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.Person

@Schema(
    description = "Represantasjon av en person",
)
@Serializable
data class PersonDto (
    @Schema(description = "Fornavn på personen", required = true)
    val fornavn: String,

    @Schema(description = "Etternavn på personen", required = true)
    val etternavn: String,
) {
    fun toPerson(): Person =
        Person(
            fornavn = fornavn,
            etternavn = etternavn,
        )
}