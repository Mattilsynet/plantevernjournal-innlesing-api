package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.Dosering
import no.mattilsynet.plantevernjournal.api.domain.Plantevernmiddel

@Schema(
    description = "Liste av plantevernmiddel og mengde som ble brukt",
)
@Serializable
data class PlantevernmiddelDto(
    @Schema(
        description = "Autorisasjonsnummer på plantevernmiddelet som ble brukt", required = true,
    )
    val autorisasjonsnummer: String,

    @Schema(
        description = "Dosering av plantevernmiddelet som ble brukt", required = true,
    )
    val dosering: DoseringDto,
) {
    fun toPlantevernmiddel() =
        Plantevernmiddel(
            autorisasjonsnummer = autorisasjonsnummer,
            dosering = dosering.toDosering(),
        )
}
