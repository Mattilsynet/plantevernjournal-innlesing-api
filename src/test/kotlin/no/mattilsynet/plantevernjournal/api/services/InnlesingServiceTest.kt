package no.mattilsynet.plantevernjournal.api.services

import kotlin.uuid.ExperimentalUuidApi
import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.mocks.domain.SlettInnsendingMocker.createSlettInnsendingMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.FroeEllerFormeringsMatrialeDtoMocker.createFroeEllerFormeringsMaterialeDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.InnendoersBrukDtoMocker.createInnendoersBrukDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.UtendoersBrukDtoMocker.createUtendoersBrukDtoMock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any

@OptIn(ExperimentalUuidApi::class, ExperimentalSerializationApi::class)
internal class InnlesingServiceTest {

    private lateinit var innlesingService: InnlesingService

    private val natsService = mock(NatsService::class.java)

    @BeforeEach
    fun setUp() {
        innlesingService = InnlesingService(
            eppoService = mock(),
            natsService = natsService,
        )
    }

    @Test
    fun `postFroeEllerFormeringsMateriale poster til nats og returnerer opprettet og id`() {
        // Given:
        val froeEllerFormeringsMatrialeDtoMock = createFroeEllerFormeringsMaterialeDtoMock()

        // When:
        innlesingService.postFroeEllerFormeringsMateriale(
            froeEllerFormeringsMatrialeDto = froeEllerFormeringsMatrialeDtoMock,
            innsender = "innsender",
            paaVegneAv = "paaVegneAv",
        ).also { froeEllerFormeringsMatrialeResponsDto ->
            assertNotNull(froeEllerFormeringsMatrialeResponsDto.id)
            assertNotNull(froeEllerFormeringsMatrialeResponsDto.opprettet)
        }

        // Then:
        verify(natsService, times(1))
            .publishJournalForFroeEllerFormeringsmateriale(any())

    }

    @Test
    fun `postInnendoersBruk poster til nats og returnerer opprettet og id`() {
        // Given:
        val innendoersBrukDtoMock = createInnendoersBrukDtoMock()

        // When:
        innlesingService.postInnendoersBruk(
            innendoersBrukDto = innendoersBrukDtoMock,
            innsender = "innsender",
            paaVegneAv = "paaVegneAv",
        ).also { innendoersBrukResponsDto ->
            assertNotNull(innendoersBrukResponsDto.id)
            assertNotNull(innendoersBrukResponsDto.opprettet)
        }

        // Then:
        verify(natsService, times(1))
            .publishJournalForInnendoersBruk(any())

    }

    @Test
    fun `postUtendoersBruk poster til nats og returnerer opprettet og id`() {
        // Given:
        val utendoersBrukDto = createUtendoersBrukDtoMock()

        // When:
        innlesingService.postUtendoersBruk(
            innsender = "innsender",
            paaVegneAv = "paaVegneAv",
            utendoersBrukDto = utendoersBrukDto,
        ).also { utendoersBrukResponsDto ->
            assertNotNull(utendoersBrukResponsDto.id)
            assertNotNull(utendoersBrukResponsDto.opprettet)
        }

        // Then:
        verify(natsService, times(1))
            .publishJournalForUtendoersBruk(any())

    }

    @Test
    fun `deleteUtendoersBruk poster til nats`() {
        // Given:
        val slettInnsendingMock = createSlettInnsendingMock()

        // When:
        innlesingService.deleteUtendoersBruk(
            id = slettInnsendingMock.id,
            innsender = slettInnsendingMock.innsender,
            paaVegneAv = slettInnsendingMock.paaVegneAv,
        )

        // Then:
        verify(natsService, times(1))
            .publishSlettJournalForUtendoersBruk(slettInnsendingMock)

    }

    @Test
    fun `deleteInnendoersBruk poster til nats`() {
        // Given:
        val slettInnsendingMock = createSlettInnsendingMock()

        // When:
        innlesingService.deleteInnendoersBruk(
            id = slettInnsendingMock.id,
            innsender = slettInnsendingMock.innsender,
            paaVegneAv = slettInnsendingMock.paaVegneAv,
        )

        // Then:
        verify(natsService, times(1))
            .publishSlettJournalForInnendoersBruk(slettInnsendingMock)

    }

    @Test
    fun `deleteFroeEllerFormeringsMatriale poster til nats`() {
        // Given:
        val slettInnsendingMock = createSlettInnsendingMock()

        // When:
        innlesingService.deleteFroeEllerFormeringsMatriale(
            id = slettInnsendingMock.id,
            innsender = slettInnsendingMock.innsender,
            paaVegneAv = slettInnsendingMock.paaVegneAv,
        )

        // Then:
        verify(natsService, times(1))
            .publishSlettJournalForFroeEllerFormeringsmateriale(slettInnsendingMock)

    }

}
