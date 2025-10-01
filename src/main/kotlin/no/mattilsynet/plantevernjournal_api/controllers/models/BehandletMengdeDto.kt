package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.shared.Enhet


@Schema(
    description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø"
)
data class BehandletMengdeDto(
    @Schema(
        description = "Enhet av behandlet mengde. Må være i kg, tonn eller antall frø", required = true,
    )
    val enhet: Enhet,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale", required = true,
    )
    val verdi: Double,
)
