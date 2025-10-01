package no.mattilsynet.plantevernjournal_api.domain

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal_api.shared.Bruksomraade
import java.time.LocalDate

@Schema(
    description = "Journalfelter for bruk av plantevernmidler på frø eller formeringsmateriale"
)
data class FroeEllerFormeringsMatriale(
    @Schema(
        description = "Frø eller formeringsmateriale som ble behandlet av plantevernmidler", required = true
    )
    val behandledeVekster: BehandledeVekster,

    @Schema(
        description = "Datoen man behandlet frø eller formeringsmateriale med plantevernmidler", required = true
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Geografiskområde man behandlet frø eller formeringsmateriale", required = true
    )
    val behandletOmraade: BehandletOmraade,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø", required = true
    )
    val behandletMengde: BehandletMengde,

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
    val plantevernmiddel: List<Plantevernmiddel>,
) {
    init {
        if(bruksomraade == Bruksomraade.JORDBRUK){
        require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }

}