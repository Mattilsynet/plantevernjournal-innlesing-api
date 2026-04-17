package no.mattilsynet.plantevernjournal.api.mocks.dto

import java.time.LocalDate
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.mocks.dto.BehandletVekstDtoMocker.createBehandletVekstDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.MengdeDtoMocker.createMengdeDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PersonDtoMocker.createPersonDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PlantevernmiddelDtoMocker.createPlantevernmiddelDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureCollectionMocker.createFeatureCollectionMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureMocker.createFeatureMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.PointMocker.createPointMock
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade

object InnendoersBrukDtoMocker {

    fun createInnendoersBrukDtoMock() =
        InnendoersBrukDto(
            behandledeOmraader = createFeatureCollectionMock(createFeatureMock(createPointMock())),
            behandledeVekster = listOf(createBehandletVekstDtoMock()),
            behandler = createPersonDtoMock(),
            behandletDato = LocalDate.now(),
            bruksomraade = Bruksomraade.JORDBRUK,
            bygningsnummer = "Nr 34",
            bygningsstoerrelse = createMengdeDtoMock(),
            egenReferanse = "Egen referanse",
            organisasjonsnummerEier = "987654321",
            organisasjonsnummerSproeyter = "987461232",
            plantevernmiddel = listOf(createPlantevernmiddelDtoMock()),
        )

}
