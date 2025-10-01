package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "Liste av plantevernmiddel og mengde som ble brukt",
)
data class PlantevernmiddelDto(
    @Schema(
        description = "Autorisasjonsnummer på plantevernmiddelet som ble brukt", required = true,
    )
    val autorisasjonsnummer: String,

    @Schema(
        description = "Dosering av plantevernmiddelet som ble brukt", required = true,
    )
    val dosering: DoseringDto,
)
