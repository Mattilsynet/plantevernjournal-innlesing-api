package no.mattilsynet.plantevernjournal.api.mocks.domain

import java.util.UUID
import no.mattilsynet.plantevernjournal.api.domain.SlettInnsending

object SlettInnsendingMocker {

    fun createSlettInnsendingMock(
        id: UUID = UUID.randomUUID(),
        innsender: String = "Innsender",
        paaVegneAv: String = "PaaVegneAv",
    ) =
        SlettInnsending(
            id = id,
            innsender = innsender,
            paaVegneAv = paaVegneAv,
        )

}
