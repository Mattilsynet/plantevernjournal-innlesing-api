package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.LocalDateTimeSerializer
import java.time.LocalDateTime

@kotlinx.serialization.ExperimentalSerializationApi
@Serializable
class UtendoersBruk(
    val arealBehandletOmraade: ArealBehandletOmraade,

    val behandledeVekster: BehandledeVekster,

    val behandletOmraade: BehandletOmraade,

    val bruksomraade: Bruksomraade,

    val gaardsnummer: String?,

    @Serializable(with = LocalDateTimeSerializer::class)
    val opprettet: LocalDateTime = LocalDateTime.now(),

    val plantevernmiddel: List<Plantevernmiddel>,

    @Serializable(with = LocalDateTimeSerializer::class)
    val startTid: LocalDateTime,
)
