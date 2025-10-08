package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import java.time.LocalDateTime

@Schema(
    description = "Journalfelter for utendørs bruk av plantevernmidler",
)
data class UtendoersBrukDto(
    @Schema(
        description = "Arealet man behandlet med plantevernmidler i dekar", required = true,
    )
    val arealBehandletOmraade: ArealBehandletOmraadeDto,

    @Schema(
        description = "Vekster som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: BehandledeVeksterDto,

    @Schema(
        description = "Geografisk område man behandlet med plantevernmidler", required = true,
    )
    val behandletOmraade: BehandletOmraadeDto,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Gårdsnummer til gården", required = false,
    )
    val gaardsnummer: String?,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,

    @Schema(
        description = "Dato med tidspunkt for når man behandlet vekster med plantevernmidler", required = true,
    )
    val startTid: LocalDateTime,
){
    init {
        if (bruksomraade == Bruksomraade.JORDBRUK) {
            require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }
}
