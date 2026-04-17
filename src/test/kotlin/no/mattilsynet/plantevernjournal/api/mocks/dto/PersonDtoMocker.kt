package no.mattilsynet.plantevernjournal.api.mocks.dto

import no.mattilsynet.plantevernjournal.api.controllers.models.PersonDto

object PersonDtoMocker {

    fun createPersonDtoMock(
    ) =
        PersonDto(
            etternavn = "Personetternavn",
            fornavn = "Personfornavn",
        )

}
