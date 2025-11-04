package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.LocalDateSerializer
import no.mattilsynet.plantevernjournal.api.shared.LocalDateTimeSerializer
import no.mattilsynet.plantevernjournal.api.shared.UUIDSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@kotlinx.serialization.ExperimentalSerializationApi
@Serializable
data class InnendoersBruk(
    val behandledeVekster: BehandledeVekster,

    val behandler: Person,

    @Serializable(with = LocalDateSerializer::class)
    val behandletDato: LocalDate,

    val behandledeOmraader: List<String>,

    val bruksomraade: Bruksomraade,

    val bygningsnummer: String,

    val bygningsstoerrelse: Mengde,

    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),

    val gaardsnummer: String?,

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val organisasjonsnummer: String,

    val plantevernmiddel: List<Plantevernmiddel>,
)
