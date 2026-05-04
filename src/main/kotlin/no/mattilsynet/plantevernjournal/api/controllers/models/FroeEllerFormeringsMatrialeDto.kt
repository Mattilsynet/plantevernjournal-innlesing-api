package no.mattilsynet.plantevernjournal.api.controllers.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import no.mattilsynet.plantevernjournal.api.domain.FroeEllerFormeringsMatriale
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import org.wololo.geojson.FeatureCollection
import java.time.Instant
import java.time.LocalDate


@Schema(
    description = "Journalfelter for bruk av plantevernmidler på frø eller formeringsmateriale",
)
data class FroeEllerFormeringsMatrialeDto(
    @Schema(
        description = "Liste av frø eller formeringsmateriale som ble behandlet av plantevernmidler", required = true,
    )
    val behandledeVekster: List<BehandletVekstDto>,

    @Schema(description = "Personen som har sprøytet med plantevernmidler", required = true)
    val behandler: PersonDto,

    @Schema(
        description = "Datoen man behandlet frø eller formeringsmateriale med plantevernmidler", required = true,
    )
    val behandletDato: LocalDate,

    @Schema(
        description = "Mengde behandlede frø eller formeringsmateriale i kg, tonn eller antall frø", required = true,
    )
    val behandletMengde: MengdeDto,

    @Schema(
        description = "Geografiske områder hvor man har behandlet frø eller formeringsmateriale", required = false,
    )
    val behandledeOmraader: FeatureCollection?,

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
        description = "Liste av plantevernmiddel og mengde som ble brukt",
        required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,
) {

    fun toFroeEllerFormeringsMatriale(
        innsender: String?,
        paaVegneAv: String?,
    ) =
        FroeEllerFormeringsMatriale(
            behandledeVekster = behandledeVekster.map { it.toBehandletVekst() },
            behandler = behandler.toPerson(),
            behandletDato = behandletDato,
            behandletMengde = behandletMengde.toMengde(),
            behandledeOmraader = behandledeOmraader?.toBehandledeOmraader(),
            bruksomraade = bruksomraade,
            egenReferanse = egenReferanse,
            innsender = innsender,
            opprettet = Instant.now(),
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            paaVegneAv = paaVegneAv,
            plantevernmiddel = plantevernmiddel.map { it.toPlantevernmiddel() },
        )
}
