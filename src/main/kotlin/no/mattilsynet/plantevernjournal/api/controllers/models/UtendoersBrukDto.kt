package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.domain.UtendoersBruk
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import org.wololo.geojson.FeatureCollection

@Schema(
    description = "Journalfelter for utendørs bruk av plantevernmidler",
)
data class UtendoersBrukDto(
    @Schema(
        description = "Arealet man behandlet med plantevernmidler i dekar", required = true,
    )
    val arealBehandletOmraade: MengdeDto,

    @Schema(
        description = "Liste med vekster som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: List<BehandletVekstDto>,

    @Schema(description = "Personen som har sprøytet med plantevernmiddler", required = true)
    val behandler: PersonDto,

    @Schema(
        description = "Geografiske områder hvor man har behandlet med plantevernmidler",
        required = false,
    )
    val behandledeOmraader: FeatureCollection?,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(description = "Fritekstfelt hvor man kan legge inn informasjon for egen nytte", required = false)
    val egenReferanse: String?,

    @Schema(
        description = "Gårdsnummer til gården", required = false,
    )
    val gaardsnummer: String?,

    @Schema(description = "Organisasjonsnummer til den som spørytet med plantevernmidler", required = true)
    @Pattern(regexp = "^[89]\\d{8}", message = "Organisasjonsnummer må starte med 8 eller 9, og være 9 siffer")
    val organisasjonsnummer: String,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,

    @Schema(
        description = "Dato med tidspunkt for når man behandlet vekster med plantevernmidler", required = true,
    )
    val startTid: LocalDateTime,
) {
    init {
        if (bruksomraade == Bruksomraade.JORDBRUK) {
            require(!gaardsnummer.isNullOrBlank())
            { "Hvis bruksområde er jordbruk kan ikke gårdsnummer være tomt" }
        }
    }

    @ExperimentalSerializationApi
    @kotlin.uuid.ExperimentalUuidApi
    fun toUtendoersBrukDto() =
        UtendoersBruk(
            arealBehandletOmraade = arealBehandletOmraade.toMengde(),
            behandledeOmraader = behandledeOmraader?.toBehandledeOmraader(),
            behandledeVekster = behandledeVekster.map { it.toBehandletVekst() },
            behandler = behandler.toPerson(),
            bruksomraade = bruksomraade,
            egenReferanse = egenReferanse,
            gaardsnummer = gaardsnummer,
            organisasjonsnummer = organisasjonsnummer,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() },
            startTid = startTid,
        )

}
