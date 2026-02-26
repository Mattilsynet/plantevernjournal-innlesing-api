package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.UtendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.wololo.geojson.FeatureCollection
import java.time.LocalDateTime
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi

@ExperimentalSerializationApi
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

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val organisasjonsnummerEier: String,

    val organisasjonsnummerSproeyter: String,

    val plantevernmiddel: List<Plantevernmiddel>,

    @Serializable(with = LocalDateTimeSerializer::class)
    val startTid: LocalDateTime,
) {

    @ExperimentalSerializationApi
    @ExperimentalUuidApi
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
