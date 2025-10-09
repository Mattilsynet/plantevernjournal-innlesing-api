package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.FroeEllerFormeringsMatriale
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.LocalDateSerializer
import java.time.LocalDate


@Schema(
    description = "Journalfelter for bruk av plantevernmidler på frø eller formeringsmateriale",
)
@Serializable
@kotlinx.serialization.ExperimentalSerializationApi
@kotlin.uuid.ExperimentalUuidApi
data class FroeEllerFormeringsMatrialeDto(
    @Schema(
        description = "Frø eller formeringsmateriale som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: BehandledeVeksterDto,

    @Schema(
        description = "Datoen man behandlet frø eller formeringsmateriale med plantevernmidler", required = true,
    )
    @Serializable(with = LocalDateSerializer::class)
    val behandletDato: LocalDate,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø", required = true,
    )
    val behandletMengde: BehandletMengdeDto,

    @Schema(
        description = "Geografisk område man behandlet frø eller formeringsmateriale", required = true,
    )
    val behandletOmraade: BehandletOmraadeDto,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Gårdsnummer til gården",
        required = false,
    )
    val gaardsnummer: String?,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt",
        required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,
) {
    init {
        if (bruksomraade == Bruksomraade.JORDBRUK) {
        require(!gaardsnummer.isNullOrBlank()) { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }

    @kotlinx.serialization.ExperimentalSerializationApi
    @kotlin.uuid.ExperimentalUuidApi
    fun toFroeEllerFormeringsMatriale() =
        FroeEllerFormeringsMatriale(
            behandledeVekster = behandledeVekster.toBehandledeVekster(),
            behandletDato = behandletDato,
            behandletMengde = behandletMengde.toBehandletMengde(),
            behandletOmraade = behandletOmraade.toBehandletOmraade(),
            bruksomraade = bruksomraade,
            gaardsnummer = gaardsnummer,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() }
        )

}
