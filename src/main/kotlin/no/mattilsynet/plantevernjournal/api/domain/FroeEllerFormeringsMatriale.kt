package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.FroeEllerFormeringsMatrialeResponsDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.wololo.geojson.FeatureCollection
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@kotlinx.serialization.ExperimentalSerializationApi
@kotlin.uuid.ExperimentalUuidApi
@Serializable
data class FroeEllerFormeringsMatriale(
    val behandledeVekster: List<BehandletVekst>,

    @Serializable(with = LocalDateSerializer::class)
    val behandletDato: LocalDate,

    val behandler: Person,

    val behandletMengde: Mengde,

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
) {
    fun toFroeEllerFormeringsMatrialeResponsDto(
        behandledeOmraader: FeatureCollection?,
        behandledeVekster: List<BehandletVekstDto>,
        plantevernmiddel: List<PlantevernmiddelDto>,
    ) =
        FroeEllerFormeringsMatrialeResponsDto(
            behandledeVekster = behandledeVekster,
            behandler = behandler.toPersonDto(),
            behandletDato = behandletDato,
            behandletMengde = behandletMengde.toMengdeDto(),
            behandledeOmraader = behandledeOmraader,
            bruksomraade = bruksomraade,
            egenReferanse = egenReferanse,
            id = id,
            opprettet = opprettet,
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            plantevernmiddel = plantevernmiddel,
        )

}
