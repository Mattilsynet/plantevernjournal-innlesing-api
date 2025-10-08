package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal.api.shared.Enhet

@Schema(
    description = "Arealet man behandlet med plantevernmidler i dekar",
)
data class ArealBehandletOmraadeDto (
    @Schema(
        description = "Enheten som er brukt. Må være i dekar", required = true,
    )
    val enhet: Enhet,

    @Schema(
        description = "Hvor stort arealet er", required = true,
    )
    val verdi: Double,
)
