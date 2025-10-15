package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal.api.domain.InnendoersBruk
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import java.time.LocalDate

@Schema(
    description = "Journalfelter for innendørs bruk av plantevernmidler",
)
data class InnendoersBrukDto(
    @Schema(
        description = "Vekster som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: BehandledeVeksterDto,

    @Schema(description = "Personen som har sprøytet med plantevernmiddler", required = true)
    val behandler: PersonDto,

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
    val bygningsstoerrelse: MengdeDto,

    @Schema(
        description = "Gårdsnummer til gården", required = false,
    )
    val gaardsnummer: String?,

    @Schema(description = "Organisasjonsnummer til den som spørytet med plantevernmidler", required = true)
    val organisasjonsnummer: String,

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

    @kotlinx.serialization.ExperimentalSerializationApi
    @kotlin.uuid.ExperimentalUuidApi
    fun toInnendoersBruk() =
        InnendoersBruk(
            behandledeVekster = behandledeVekster.toBehandledeVekster(),
            behandletDato = behandletDato,
            behandler = behandler.toPerson(),
            behandletOmraade = behandletOmraade.toBehandletOmraade(),
            bygningsnummer = bygningsnummer,
            bygningsstoerrelse = bygningsstoerrelse.toMengde(),
            bruksomraade = bruksomraade,
            gaardsnummer = gaardsnummer,
            organisasjonsnummer = organisasjonsnummer,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() },
        )

}
