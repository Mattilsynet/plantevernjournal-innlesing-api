package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.domain.Enhet

@Schema(
    description = "Størrelse på bygningen der det ble sprøytet"
)
data class BygningsstoerrelseDto(
    @Schema(
        description = "Enhet på størrelse på bygningen, må være enten være i kvadratmeter eller i kubikkmeter",
        required = true,
    )
    val enhet: Enhet,

    @Schema(
        description = "Størrelse på bygningen", required = true
    )
    val verdi: Double,

)
