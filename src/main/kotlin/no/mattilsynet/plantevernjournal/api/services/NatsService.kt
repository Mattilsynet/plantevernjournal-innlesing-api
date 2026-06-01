@file:OptIn(ExperimentalSerializationApi::class)

package no.mattilsynet.plantevernjournal.api.services

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import no.mattilsynet.plantevernjournal.api.domain.FroeEllerFormeringsMatriale
import no.mattilsynet.plantevernjournal.api.domain.InnendoersBruk
import no.mattilsynet.plantevernjournal.api.domain.SlettInnsending
import no.mattilsynet.plantevernjournal.api.domain.UtendoersBruk
import no.mattilsynet.plantevernjournal.api.nats.jetstream.subjects.JetStreamSubjectBuilder
import no.mattilsynet.virtualnats.virtualnatscore.VirtualNats
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NatsService(
    nats: VirtualNats,
) {

    private val jetStream = nats.jetStream()
    private val logger = LoggerFactory.getLogger(javaClass)

    fun publishJournalForFroeEllerFormeringsmateriale(
        froeEllerFormeringsMatriale: FroeEllerFormeringsMatriale,
    ) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalFroeV1(froeEllerFormeringsMatriale.id.toString()),
            body = Json.encodeToString(froeEllerFormeringsMatriale).toByteArray(),
        )
        logger.info("Melding postet til publishJournalForFroeEllerFormeringsmateriale for id " +
                froeEllerFormeringsMatriale.id)
    }

    fun publishJournalForInnendoersBruk(
        innendoersBruk: InnendoersBruk,
    ) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalInnendoersV1(innendoersBruk.id.toString()),
            body = Json.encodeToString(innendoersBruk).toByteArray(),
        )
        logger.info("Melding postet til publishJournalForInnendoersBruk for id " +
                innendoersBruk.id)
    }

    fun publishJournalForUtendoersBruk(
        utendoersBruk: UtendoersBruk,
    ) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalUtendoersV1(utendoersBruk.id.toString()),
            body = Json.encodeToString(utendoersBruk).toByteArray(),
        )
        logger.info("Melding postet til publishJournalForUtendoersBruk for id " +
                utendoersBruk.id)
    }

    fun publishSlettJournalForUtendoersBruk(slettInnsending: SlettInnsending) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalSlettUtendoersV1(id = slettInnsending.id.toString()),
            body = Json.encodeToString(slettInnsending).toByteArray(),
        )
        logger.info("Melding postet til publishSlettJournalForUtendoersBruk for id " +
                slettInnsending.id)
    }

    fun publishSlettJournalForInnendoersBruk(slettInnsending: SlettInnsending) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalSlettInnendoersV1(id = slettInnsending.id.toString()),
            body = Json.encodeToString(slettInnsending).toByteArray(),
        )
        logger.info("Melding postet til publishSlettJournalForInnendoersBruk for id " +
                slettInnsending.id)
    }

    fun publishSlettJournalForFroeEllerFormeringsmateriale(slettInnsending: SlettInnsending) {
        jetStream.publish(
            subject = JetStreamSubjectBuilder
                .plantevernjournalSlettFroeV1(id = slettInnsending.id.toString()),
            body = Json.encodeToString(slettInnsending).toByteArray(),
        )
        logger.info("Melding postet til publishSlettJournalForFroeEllerFormeringsmateriale for id " +
                slettInnsending.id)
    }
}
