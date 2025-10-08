package no.mattilsynet.plantevernjournal.api.nats.jetstream.subjects

import no.mattilsynet.fisk.libs.nats.virtual.JetStreamSubject

object JetStreamSubjectBuilder {
    fun plantevernjournalFroeV1(id: String = "*") = JetStreamSubject(
        stream = "plantevernjournal_innlesing_froe_v1",
        subject = "mattilsynet.plantevernjournal.innlesing.froe.v1.$id",
    )
}
