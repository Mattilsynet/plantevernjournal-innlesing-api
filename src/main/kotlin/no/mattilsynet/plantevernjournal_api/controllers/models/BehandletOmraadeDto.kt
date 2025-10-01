package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Geografisk område man behandlet med plantevernmidler"
)
data class BehandletOmraadeDto(
    @Schema(
        description = "Her skal det være geografiske data på et format vi bestemmer etter hvert"
    )
    val omraade: String,
)
