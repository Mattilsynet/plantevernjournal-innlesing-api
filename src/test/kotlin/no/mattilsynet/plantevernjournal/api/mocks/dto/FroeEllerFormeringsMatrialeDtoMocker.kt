package no.mattilsynet.plantevernjournal.api.mocks.dto

import java.time.LocalDate
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.mocks.dto.BehandletVekstDtoMocker.createBehandletVekstDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.MengdeDtoMocker.createMengdeDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PersonDtoMocker.createPersonDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PlantevernmiddelDtoMocker.createPlantevernmiddelDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureCollectionMocker.createFeatureCollectionMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureMocker.createFeatureMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.PointMocker.createPointMock
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade

object FroeEllerFormeringsMatrialeDtoMocker {

    fun createFroeEllerFormeringsMaterialeDtoMock() =
        FroeEllerFormeringsMatrialeDto(
            behandledeVekster = listOf(createBehandletVekstDtoMock()),
            behandler = createPersonDtoMock(),
            behandletDato = LocalDate.now(),
            behandletMengde = createMengdeDtoMock(),
            behandledeOmraader = createFeatureCollectionMock(createFeatureMock(createPointMock())),
            bruksomraade = Bruksomraade.JORDBRUK,
            egenReferanse = "Egen referanse",
            organisasjonsnummerEier = "987654321",
            organisasjonsnummerSproeyter = "987461232",
            plantevernmiddel = listOf(createPlantevernmiddelDtoMock()),
        )

}
