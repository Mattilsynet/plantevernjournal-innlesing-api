package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.domain.Enhet

@Schema(
    description = "Dosering av plantevernmiddelet som ble brukt"
)
data class Dosering(
    @Schema(
        description = "Enhet på doseringen", required = true
    )
    val enhet: Enhet,

    @Schema(
        description = "Mengde av plantevernmiddelet som ble brukt", required = true
    )
    val verdi: Double,

)
