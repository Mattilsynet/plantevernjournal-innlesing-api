package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandletVekst


@Schema(
    description = "Vekster som ble behandlet av plantevernmidler",
)
@Serializable
data class BehandletVekstDto(
    @Schema(
        description = "Fra BBCH-skalaen til den behandlede veksten. To siffer.", required = false,
    )
    val bbchFase: String?,

    @Schema(
        description = "Eppokode av vekst som er behandlet. Dersom det ikke finnes eppokode for planten, så velges " +
                "en overordnet eppokode, og sort kan angis i eget felt.",
        required = true,
    )
    val eppoKode: String,

    @Schema(
        description = "Partinummer, kun ved bruk av plantevernmidler på frø eller formeringsmateriale",
        required = false,
    )
    val partinummer: Int?,

    @Schema(
        description = "Her kan sort legges inn, dersom eppokode ikke er nok beskrivende.", required = false,
    )
    val sort: String?,
) {
    fun toBehandletVekst() =
        BehandletVekst(
            bbchFase = bbchFase,
            eppoKode = eppoKode,
            partinummer = partinummer,
            sort = sort,
        )
}
