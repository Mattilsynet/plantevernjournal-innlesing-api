package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import no.mattilsynet.plantevernjournal.api.domain.InnendoersBruk
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import org.wololo.geojson.FeatureCollection
import java.time.LocalDate

@Schema(
    description = "Journalfelter for innendørs bruk av plantevernmidler",
)
data class InnendoersBrukDto(
    @Schema(
        description = "Liste med vekster som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: List<BehandletVekstDto>,

    @Schema(description = "Personen som har sprøytet med plantevernmidler", required = true)
    val behandler: PersonDto,

    @Schema(
        description = "Datoen man behandlet vekster med plantevernmidler", required = true,
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Geografiske områder hvor man har behandlet med plantevernmidler", required = false,
    )
    val behandledeOmraader: FeatureCollection?,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(
        description = "Bygningsnummer der det ble sprøytet. Usikkerhet rundt hvordan det blir, kanskje FKB_Bygning " +
                " eller en intern beskrivelse for hver gård",
        required = false,
    )
    val bygningsnummer: String?,

    @Schema(
        description = "Størrelse på bygningen der det ble sprøytet", required = true,
    )
    val bygningsstoerrelse: MengdeDto,

    @Schema(description = "Fritekstfelt hvor man kan legge inn informasjon for egen nytte", required = false)
    val egenReferanse: String?,

    @Schema(description = "Organisasjonsnummer til den som eier arealet det ble sprøytet på", required = true)
    @field:Pattern(regexp = "^[89]\\d{8}", message = "Organisasjonsnummer må starte med 8 eller 9, og være 9 siffer")
    val organisasjonsnummerEier: String,

    @Schema(description = "Organisasjonsnummer til den som sprøytet med plantevernmidler", required = true)
    @field:Pattern(regexp = "^[89]\\d{8}", message = "Organisasjonsnummer må starte med 8 eller 9, og være 9 siffer")
    val organisasjonsnummerSproeyter: String,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,
) {

    @kotlinx.serialization.ExperimentalSerializationApi
    @kotlin.uuid.ExperimentalUuidApi
    fun toInnendoersBruk(
        innsender: String?,
    ) =
        InnendoersBruk(
            behandledeOmraader = behandledeOmraader?.toBehandledeOmraader(),
            behandledeVekster = behandledeVekster.map { it.toBehandletVekst() },
            behandler = behandler.toPerson(),
            behandletDato = behandletDato,
            bruksomraade = bruksomraade,
            bygningsnummer = bygningsnummer,
            bygningsstoerrelse = bygningsstoerrelse.toMengde(),
            egenReferanse = egenReferanse,
            innsender = innsender,
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() },
        )

}
