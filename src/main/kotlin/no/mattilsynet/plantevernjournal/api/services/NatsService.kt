@file:OptIn(ExperimentalSerializationApi::class)

package no.mattilsynet.plantevernjournal.api.services

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import no.mattilsynet.fisk.libs.virtualnats.VirtualNats
import no.mattilsynet.plantevernjournal.api.domain.FroeEllerFormeringsMatriale
import no.mattilsynet.plantevernjournal.api.nats.jetstream.subjects.JetStreamSubjectBuilder
import org.springframework.stereotype.Service

@kotlin.uuid.ExperimentalUuidApi
@Service
class NatsService(
    nats: VirtualNats,
) {

    private val jetStream = nats.jetStream()

    fun publishJournalForFroeEllerFormeringsmateriale(
        froeEllerFormeringsMatriale: FroeEllerFormeringsMatriale,
    ) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalFroeV1(froeEllerFormeringsMatriale.id.toString()),
            body = Json.encodeToString(froeEllerFormeringsMatriale).toByteArray(),
        )
    }
}
