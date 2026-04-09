package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto
import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.InnendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.wololo.geojson.FeatureCollection
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@kotlinx.serialization.ExperimentalSerializationApi
@Serializable
data class InnendoersBruk(
    val behandledeVekster: List<BehandletVekst>,

    val behandler: Person,

    @Serializable(with = LocalDateSerializer::class)
    val behandletDato: LocalDate,

    val behandledeOmraader: List<String>?,

    val bruksomraade: Bruksomraade,

    val bygningsnummer: String?,

    val bygningsstoerrelse: Mengde,

    val egenReferanse: String?,

    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),

    val innsender: String?,

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val organisasjonsnummerEier: String,

    val organisasjonsnummerSproeyter: String,

    val paaVegneAv: String?,

    val plantevernmiddel: List<Plantevernmiddel>,
) {
    fun toInnendoersBrukResponsDto(
        behandledeOmraader: FeatureCollection?,
        behandledeVekster: List<BehandletVekstDto>,
        plantevernmiddel: List<PlantevernmiddelDto>,
    ) =
        InnendoersBrukResponsDto(
            behandledeOmraader = behandledeOmraader,
            behandledeVekster = behandledeVekster,
            behandler = behandler.toPersonDto(),
            behandletDato = behandletDato,
            bruksomraade = bruksomraade,
            bygningsnummer = bygningsnummer,
            bygningsstoerrelse = bygningsstoerrelse.toMengdeDto(),
            egenReferanse = egenReferanse,
            id = id,
            opprettet = opprettet,
            organisasjonsnummerEier = organisasjonsnummerEier,
            organisasjonsnummerSproeyter = organisasjonsnummerSproeyter,
            plantevernmiddel = plantevernmiddel,
        )
}
