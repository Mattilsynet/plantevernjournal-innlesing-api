package no.mattilsynet.plantevernjournal_api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.domain.Bruksomraade
import java.time.LocalDate

@Schema(
    description = "Journalfelter for bruk av plantevernmidler på frø eller formeringsmateriale"
)
data class FroeEllerFormeringsMatrialeDto(
    @Schema(
        description = "Frø eller formeringsmateriale som ble behandlet av plantevernmidler", required = true
    )
    val behandledeVekster: BehandledeVeksterDto,

    @Schema(
        description = "Datoen man behandlet frø eller formeringsmateriale med plantevernmidler", required = true
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø", required = true
    )
    val behandletMengde: BehandletMengdeDto,

    @Schema(
        description = "Geografiskområde man behandlet frø eller formeringsmateriale", required = true
    )
    val behandletOmraade: BehandletOmraadeDto,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Gårdsnummer til gården",
        required = false
    )
    val gaardsnummer: String?,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt",
        required = true
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,
) {
    init {
        if(bruksomraade == Bruksomraade.JORDBRUK){
        require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }

}
