package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.shared.Bruksomraade
import java.time.LocalDate


@Schema(
    description = "Journalfelter for innendørs bruk av plantevernmidler",
)
data class InnendoersBrukDto(
    @Schema(
        description = "Vekster som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: BehandledeVeksterDto,

    @Schema(
        description = "Datoen man behandlet vekster med plantevernmidler", required = true,
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Geografisk område man behandlet med plantevernmidler", required = true,
    )
    val behandletOmraade: BehandletOmraadeDto,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Bygningsnummer der det ble sprøytet", required = true,
    )
    val bygningsnummer: String,

    @Schema(
        description = "Størrelse på bygningen der det ble sprøytet", required = true,
    )
    val bygningsstoerrelse: BygningsstoerrelseDto,

    @Schema(
        description = "Gårdsnummer til gården", required = false,
    )
    val gaardsnummer: String?,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,
){
    init {
        if (bruksomraade == Bruksomraade.JORDBRUK) {
            require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }
}
