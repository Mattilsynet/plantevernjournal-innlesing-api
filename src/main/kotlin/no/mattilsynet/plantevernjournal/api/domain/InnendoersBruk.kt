package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.LocalDateSerializer
import no.mattilsynet.plantevernjournal.api.shared.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime

@kotlinx.serialization.ExperimentalSerializationApi
@Serializable
data class InnendoersBruk(
    val behandledeVekster: BehandledeVekster,

    @Serializable(with = LocalDateSerializer::class)
    val behandletDato: LocalDate,

    val behandletOmraade: BehandletOmraade,

    val bruksomraade: Bruksomraade,

    val bygningsnummer: String,

    val bygningsstoerrelse: Bygningsstoerrelse,

    val gaardsnummer: String?,

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val plantevernmiddel: List<Plantevernmiddel>,
)
