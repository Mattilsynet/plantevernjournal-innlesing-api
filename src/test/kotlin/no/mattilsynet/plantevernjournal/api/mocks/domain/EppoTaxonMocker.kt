package no.mattilsynet.plantevernjournal.api.mocks.domain

import no.mattilsynet.plantevernjournal.api.clients.models.EppoTaxon

object EppoTaxonMocker {

    fun createEppoTaxonMock(
        eppoKode: String = "eppokode",
        level: Int = 8,
        type: String = "Species",
    ) =
        EppoTaxon(
            eppocode = eppoKode,
            level = level,
            prefname = "Plantenavnet",
            type = type,
        )
}
