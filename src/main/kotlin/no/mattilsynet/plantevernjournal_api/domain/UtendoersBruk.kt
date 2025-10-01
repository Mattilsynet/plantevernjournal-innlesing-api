package no.mattilsynet.plantevernjournal_api.domain

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.shared.Bruksomraade
import java.time.LocalDate
import java.time.LocalTime


@Schema(
    description = "Journalfelter for utendørs bruk av plantevernmidler"
)
data class UtendoersBruk(
    @Schema(
        description = "Arealet man behandlet med plantevernmidler i dekar", required = true
    )
    val arealBehandletOmraade: ArealBehandletOmraade,

    @Schema(
        description = "Vekster som ble behandlet av plantevernmidler", required = true
    )
    val behandledeVekster: BehandledeVekster,

    @Schema(
        description = "Datoen man behandlet vekster med plantevernmidler", required = true
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Geografiskområde man behandlet med plantevernmidler", required = true
    )
    val behandletOmraade: BehandletOmraade,

    @Schema(
        description = "Tidspunket man behandlet vekster med plantevernmidler", required = true
    )
    val behandletTid: LocalTime,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Gårdsnummer til gården", required = false
    )
    val gaardsnummer: String?,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true
    )
    val plantevernmiddel: Plantevernmiddel,
){
    init {
        if(bruksomraade == Bruksomraade.JORDBRUK){
            require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }
}
