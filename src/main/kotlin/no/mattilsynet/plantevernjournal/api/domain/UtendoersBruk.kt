package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.UUIDSerializer
import java.time.LocalDateTime
import java.util.UUID

@kotlinx.serialization.ExperimentalSerializationApi
@Serializable
@Suppress("LongParameterList")
data class UtendoersBruk(
    val arealBehandletOmraade: Mengde,

    val behandledeVekster: BehandledeVekster,

    val behandler: Person,

    val behandletOmraade: BehandletOmraade,

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
)
