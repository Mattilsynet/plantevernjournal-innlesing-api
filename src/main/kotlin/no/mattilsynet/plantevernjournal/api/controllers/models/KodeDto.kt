package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable

@Schema(
    description = "Representasjon av en kodeverdi",
)
@Serializable
data class KodeDto (
    @Schema(description = "Tekstlig beskrivelse", required = true)
    val beskrivelse: String,

    @Schema(description = "Kodeverdien", required = true)
    val kode: String,
)