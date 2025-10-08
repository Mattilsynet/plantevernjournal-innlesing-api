package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Format på feilmeldinger"
)
data class FeilmeldingModellDto(

    @Schema(description = "Feilmeldingen i tekst", required = false)
    val melding: String?,

    @Schema(description = "Status til feilmeldingen", required = true)
    val status: Int,
)
