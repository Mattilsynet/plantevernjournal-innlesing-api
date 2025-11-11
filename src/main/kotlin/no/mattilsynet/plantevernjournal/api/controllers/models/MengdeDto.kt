package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.Mengde
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Enhet

@Schema(description = "Representasjon av mengde")
@Serializable
data class MengdeDto (
    @Schema(description = "Mengdens enhet", required = true)
    val enhet: Enhet,

    @Schema(description = "Mengdens verdi", required = true)
    val verdi: Double,
){
    fun toMengde(): Mengde =
        Mengde(verdi = verdi, enhet = enhet)
}