package no.mattilsynet.plantevernjournal.api.mocks.dto

import no.mattilsynet.plantevernjournal.api.controllers.models.MengdeDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Enhet

object MengdeDtoMocker {

    fun createMengdeDtoMock(
    ) =
        MengdeDto(
            enhet = Enhet.KILO_PER_KVADRATMETER,
            verdi = 5.6,
        )

}
