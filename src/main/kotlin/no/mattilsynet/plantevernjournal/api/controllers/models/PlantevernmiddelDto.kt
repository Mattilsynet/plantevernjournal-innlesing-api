package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.Plantevernmiddel

@Schema(
    description = "Liste av plantevernmiddel og mengde som ble brukt",
)
@Serializable
data class PlantevernmiddelDto(
    @Schema(
        description = "Autorisasjonsnummer på plantevernmiddelet som ble brukt. Det er foreløpig ingen validering av " +
                "at man legger til riktig. Inntil videre kan man sende inn en hvilken som helst streng",
        required = true,
    )
    val autorisasjonsnummer: String,

    @Schema(
        description = "Dosering av plantevernmiddelet som ble brukt", required = true,
    )
    val dosering: MengdeDto,
) {
    fun toPlantevernmiddel() =
        Plantevernmiddel(
            autorisasjonsnummer = autorisasjonsnummer,
            dosering = dosering.toMengde(),
        )
}
