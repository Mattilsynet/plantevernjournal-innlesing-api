package no.mattilsynet.plantevernjournal.api.nats.consumers.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandletVekst
import no.mattilsynet.plantevernjournal.api.domain.Mengde
import no.mattilsynet.plantevernjournal.api.domain.Person
import no.mattilsynet.plantevernjournal.api.domain.Plantevernmiddel
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.locationtech.jts.io.geojson.GeoJsonReader
import java.time.LocalDateTime
import java.util.UUID

@ExperimentalSerializationApi
@Serializable
@Suppress("LongParameterList")
data class UtendoersBrukFraNats(
    val arealBehandletOmraade: Mengde,

    val behandledeVekster: List<BehandletVekst>,

    val behandler: Person,

    val behandledeOmraader: List<String>,

    val bruksomraade: Bruksomraade,

    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),

    val gaardsnummer: String?,

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val organisasjonsnummer: String,

    val plantevernmiddel: List<Plantevernmiddel>,

    @Serializable(with = LocalDateTimeSerializer::class)
    val startTid: LocalDateTime,
) {
    fun toDomain() =
        UtendoersBrukDomain(
            arealBehandletOmraade = arealBehandletOmraade,
            behandledeVekster = behandledeVekster,
            behandler = behandler,
            behandledeOmraader = behandledeOmraader.map { GeoJsonReader().read(it) },
            bruksomraade = bruksomraade,
            gaardsnummer = gaardsnummer,
            id = id,
            opprettet = opprettet,
            organisasjonsnummer = organisasjonsnummer,
            plantevernmiddel = plantevernmiddel,
            startTid = startTid,
        )
}