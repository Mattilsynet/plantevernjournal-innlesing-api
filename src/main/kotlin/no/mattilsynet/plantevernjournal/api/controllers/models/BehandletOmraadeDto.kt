package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandletOmraade

@Schema(
    description = "Geografisk område man behandlet med plantevernmidler",
)
@Serializable
data class BehandletOmraadeDto(
    @Schema(
        description = "Her skal det være geografiske data på et format vi bestemmer etter hvert",
    )
    val omraade: String,
) {
    fun toBehandletOmraade() =
        BehandletOmraade(
            omraade = omraade,
        )
}
