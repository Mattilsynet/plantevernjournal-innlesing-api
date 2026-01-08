package no.mattilsynet.plantevernjournal.api.nats.jetstream.subjects

import no.mattilsynet.virtualnats.virtualnatscore.nats.virtual.JetStreamSubject

object JetStreamSubjectBuilder {
    fun plantevernjournalFroeV1(id: String = "*") = JetStreamSubject(
        stream = "plantevernjournal_innlesing_froe_v1",
        subject = "mattilsynet.plantevernjournal.innlesing.froe.v1.$id",
    )

    fun plantevernjournalInnendoersV1(id: String = "*") = JetStreamSubject(
        stream = "plantevernjournal_innlesing_innendoers_v1",
        subject = "mattilsynet.plantevernjournal.innlesing.innendoers.v1.$id",
    )

    fun plantevernjournalUtendoersV1(id: String = "*") = JetStreamSubject(
        stream = "plantevernjournal_innlesing_utendoers_v1",
        subject = "mattilsynet.plantevernjournal.innlesing.utendoers.v1.$id",
    )
}
