package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.Dosering
import no.mattilsynet.plantevernjournal.api.shared.Enhet

@Schema(
    description = "Dosering av plantevernmiddelet som ble brukt",
)
@Serializable
data class DoseringDto(
    @Schema(
        description = "Enhet på doseringen", required = true,
    )
    val enhet: Enhet,

    @Schema(
        description = "Mengde av plantevernmiddelet som ble brukt", required = true,
    )
    val verdi: Double,
) {
    fun toDosering() =
        Dosering(
            enhet = enhet,
            verdi = verdi,
        )
}
