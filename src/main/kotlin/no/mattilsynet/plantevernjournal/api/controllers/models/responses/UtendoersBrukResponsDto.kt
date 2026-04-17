package no.mattilsynet.plantevernjournal.api.controllers.models.responses

import io.swagger.v3.oas.annotations.media.Schema
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.MengdeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PersonDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import org.wololo.geojson.FeatureCollection
import java.time.Instant
import java.util.UUID

@Schema(
    description = "Journalfelter for utendørs bruk av plantevernmidler",
)
data class UtendoersBrukResponsDto(
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

    @Schema(description = "Id for å identifisere innlesingen", required = true)
    val id: UUID,

    @Schema(
        description = "Når journalføringen ble lest inn", required = true,
    )
    val opprettet: Instant,

    @Schema(description = "Organisasjonsnummer til den som eier arealet det ble sprøytet på", required = true)
    val organisasjonsnummerEier: String,

    @Schema(description = "Organisasjonsnummer til den som sprøytet med plantevernmidler", required = true)
    val organisasjonsnummerSproeyter: String,

    @Schema(
        description = "Liste av plantevernmiddel og mengde som ble brukt", required = true,
    )
    val plantevernmiddel: List<PlantevernmiddelDto>,

    @Schema(
        description = "Dato med tidspunkt for når man behandlet vekster med plantevernmidler", required = true,
    )
    val startTid: Instant,
)
