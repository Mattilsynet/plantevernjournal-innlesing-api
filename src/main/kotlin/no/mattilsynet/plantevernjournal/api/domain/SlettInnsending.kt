package no.mattilsynet.plantevernjournal.api.domain

import kotlinx.serialization.Serializable
import no.mattilsynet.plantevernjournal.api.shared.serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class SlettInnsending (

    @Serializable(with = UUIDSerializer::class)
    val id: UUID,

    val innsender: String?,

    val paaVegneAv: String?,
)
