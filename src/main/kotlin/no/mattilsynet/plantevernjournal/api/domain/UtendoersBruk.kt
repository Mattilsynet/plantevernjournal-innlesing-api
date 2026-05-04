package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.UtendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.JavaInstantSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.wololo.geojson.FeatureCollection
import java.time.Instant
import java.util.UUID

@Serializable
@Suppress("LongParameterList")
data class UtendoersBruk(
    val arealBehandletOmraade: Mengde,

    val behandledeVekster: List<BehandletVekst>,

    val behandler: Person,

    val behandledeOmraader: List<String>?,

    val bruksomraade: Bruksomraade,

    val egenReferanse: String?,

    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),

    val innsender: String?,

    @Serializable(with = JavaInstantSerializer::class)
    val opprettet: Instant,

    val organisasjonsnummerEier: String,

    val organisasjonsnummerSproeyter: String,

    val paaVegneAv: String?,

    val plantevernmiddel: List<Plantevernmiddel>,

    @Serializable(with = JavaInstantSerializer::class)
    val startTid: Instant,
) {

    fun toUtendoersBrukResponsDto(
        behandledeOmraader: FeatureCollection,
        behandledeVekster: List<BehandletVekstDto>,
        plantevernmiddel: List<PlantevernmiddelDto>,
    ) =
        UtendoersBrukResponsDto(
            arealBehandletOmraade = arealBehandletOmraade.toMengdeDto(),
            behandledeOmraader = behandledeOmraader,
            behandledeVekster = behandledeVekster,
            behandler = behandler.toPersonDto(),
            bruksomraade = bruksomraade,
            egenReferanse = egenReferanse,
            id = id,
            opprettet = opprettet,
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            plantevernmiddel = plantevernmiddel,
            startTid = startTid,
        )

}
