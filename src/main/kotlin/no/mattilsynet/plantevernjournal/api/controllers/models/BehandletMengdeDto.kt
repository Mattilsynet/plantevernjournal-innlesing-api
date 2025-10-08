package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandletMengde
import no.mattilsynet.plantevernjournal.api.shared.Enhet


@Schema(
    description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø",
)
@Serializable
data class BehandletMengdeDto(
    @Schema(
        description = "Enhet av behandlet mengde. Må være i kg, tonn eller antall frø", required = true,
    )
    val enhet: Enhet,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale", required = true,
    )
    val verdi: Double,
) {
    fun toBehandletMengde() =
        BehandletMengde(
            enhet = enhet,
            verdi = verdi,
        )
}
