package no.mattilsynet.plantevernjournal.api.mocks.dto

import no.mattilsynet.plantevernjournal.api.controllers.models.PlantevernmiddelDto
import no.mattilsynet.plantevernjournal.api.mocks.dto.MengdeDtoMocker.createMengdeDtoMock

object PlantevernmiddelDtoMocker {

    fun createPlantevernmiddelDtoMock(
    ) =
        PlantevernmiddelDto(
            dosering = createMengdeDtoMock(),
            registreringsnummer = "123",
        )

}
