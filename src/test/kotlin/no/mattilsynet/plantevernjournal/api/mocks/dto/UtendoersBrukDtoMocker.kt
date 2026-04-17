package no.mattilsynet.plantevernjournal.api.mocks.dto

import java.time.LocalDateTime
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import no.mattilsynet.plantevernjournal.api.mocks.dto.BehandletVekstDtoMocker.createBehandletVekstDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.MengdeDtoMocker.createMengdeDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PersonDtoMocker.createPersonDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.PlantevernmiddelDtoMocker.createPlantevernmiddelDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureCollectionMocker.createFeatureCollectionMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.FeatureMocker.createFeatureMock
import no.mattilsynet.plantevernjournal.api.mocks.geometry.PointMocker.createPointMock
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade

object UtendoersBrukDtoMocker {

    fun createUtendoersBrukDtoMock() =
        UtendoersBrukDto(
            arealBehandletOmraade = createMengdeDtoMock(),
            behandledeOmraader = createFeatureCollectionMock(createFeatureMock(createPointMock())),
            behandledeVekster = listOf(createBehandletVekstDtoMock()),
            behandler = createPersonDtoMock(),
            bruksomraade = Bruksomraade.JORDBRUK,
            egenReferanse = "Egen referanse",
            organisasjonsnummerEier = "987654321",
            organisasjonsnummerSproeyter = "987461232",
            plantevernmiddel = listOf(createPlantevernmiddelDtoMock()),
            startTid = LocalDateTime.now(),
        )

}
