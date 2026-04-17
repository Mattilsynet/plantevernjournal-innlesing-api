package no.mattilsynet.plantevernjournal.api.mocks.dto

import no.mattilsynet.plantevernjournal.api.controllers.models.BehandletVekstDto

object BehandletVekstDtoMocker {

    fun createBehandletVekstDtoMock(
    ) =
        BehandletVekstDto(
            bbchFase = "03",
            eppoKode = "BEMITA",
            partinummer = 5,
            sort = "Testsorten",
        )

}
