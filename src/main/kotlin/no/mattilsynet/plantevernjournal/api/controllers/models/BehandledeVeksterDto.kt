package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandledeVekster


@Schema(
    description = "Vekster som ble behandlet av plantevernmidler",
)
@Serializable
data class BehandledeVeksterDto(
    @Schema(
        description = "Fra BBCH-skalaen til den behandlede veksten. To siffer.", required = false,
    )
    val bbchFase: String?,

    @Schema(
        description = "Eppokode av vekst som er behandlet", required = true,
    )
    val eppoKode: String,

    @Schema(
        description = "Partinummer, kun ved bruk av plantevernmidler på frø eller formeringsmateriale",
        required = false,
    )
    val partinummer: Int?,
) {
    fun toBehandledeVekster() =
        BehandledeVekster(
            bbchFase = bbchFase,
            eppoKode = eppoKode,
            partinummer = partinummer,
        )
}
