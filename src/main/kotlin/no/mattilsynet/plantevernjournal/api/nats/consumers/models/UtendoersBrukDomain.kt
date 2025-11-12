package no.mattilsynet.plantevernjournal.api.nats.consumers.models

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.domain.BehandletVekst
import no.mattilsynet.plantevernjournal.api.domain.Mengde
import no.mattilsynet.plantevernjournal.api.domain.Person
import no.mattilsynet.plantevernjournal.api.domain.Plantevernmiddel
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import org.locationtech.jts.geom.Geometry
import java.time.LocalDateTime
import java.util.UUID

@Suppress("LongParameterList")
data class UtendoersBrukDomain(
    val arealBehandletOmraade: Mengde,

    val behandledeVekster: List<BehandletVekst>,

    val behandler: Person,

    val behandledeOmraader: List<Geometry>,

    val bruksomraade: Bruksomraade,

    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),

    val gaardsnummer: String?,

    val opprettet: LocalDateTime = LocalDateTime.now(),

    val organisasjonsnummer: String,

    val plantevernmiddel: List<Plantevernmiddel>,

    val startTid: LocalDateTime,
)
