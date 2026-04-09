package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.domain.UtendoersBruk
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import org.wololo.geojson.FeatureCollection
import java.time.LocalDateTime

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

    @Schema(description = "Personen som har sprøytet med plantevernmidler", required = true)
    val behandler: PersonDto,

    @Schema(
        description = "Geografiske områder hvor man har behandlet med plantevernmidler",
        required = true,
    )
    val behandledeOmraader: FeatureCollection,

    @Schema(
        description = "Hvilket bruksområde har behandlingen", required = true,
    )
    val bruksomraade: Bruksomraade,

    @Schema(description = "Fritekstfelt hvor man kan legge inn informasjon for egen nytte", required = false)
    val egenReferanse: String?,

    @Schema(description = "Organisasjonsnummer til den som eier arealet det ble sprøytet på", required = true)
    @field:Pattern(regexp = "\\d{9}", message = "Organisasjonsnummer må starte med 8 eller 9, og være 9 siffer")
    val organisasjonsnummerEier: String,

    @Schema(description = "Organisasjonsnummer til den som sprøytet med plantevernmidler", required = true)
    @field:Pattern(regexp = "\\d{9}", message = "Organisasjonsnummer må starte med 8 eller 9, og være 9 siffer")
    val organisasjonsnummerSproeyter: String,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,

    @Schema(
        description = "Dato med tidspunkt for når man behandlet vekster med plantevernmidler", required = true,
    )
    val startTid: LocalDateTime,
) {
    @ExperimentalSerializationApi
    @kotlin.uuid.ExperimentalUuidApi
    fun toUtendoersBruk(
        innsender: String?,
    ) =
        UtendoersBruk(
            arealBehandletOmraade = arealBehandletOmraade.toMengde(),
            behandledeOmraader = behandledeOmraader.toBehandledeOmraader(),
            behandledeVekster = behandledeVekster.map { it.toBehandletVekst() },
            behandler = behandler.toPerson(),
            bruksomraade = bruksomraade,
            egenReferanse = egenReferanse,
            innsender = innsender,
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() },
            startTid = startTid,
        )

}
