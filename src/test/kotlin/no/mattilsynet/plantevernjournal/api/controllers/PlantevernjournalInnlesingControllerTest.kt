package no.mattilsynet.plantevernjournal.api.controllers

import no.mattilsynet.plantevernjournal.api.mocks.dto.FroeEllerFormeringsMatrialeDtoMocker.createFroeEllerFormeringsMaterialeDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.InnendoersBrukDtoMocker.createInnendoersBrukDtoMock
import no.mattilsynet.plantevernjournal.api.mocks.dto.UtendoersBrukDtoMocker.createUtendoersBrukDtoMock
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.services.InnlesingService
import no.mattilsynet.plantevernjournal.api.services.NatsService
import no.mattilsynet.virtualnats.virtualnatscore.VirtualNats
import no.mattilsynet.virtualnats.virtualnatsspring.SpringVirtualNatsStarter
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import java.nio.charset.StandardCharsets
import java.util.UUID

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Suppress("UnusedPrivateProperty")
internal class PlantevernjournalInnlesingControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockitoBean
    private lateinit var nats: VirtualNats

    @MockitoBean
    private lateinit var natsService: NatsService

    @MockitoBean
    private lateinit var eppoKvConsumer: EppoKvConsumer

    @MockitoBean
    private lateinit var springVirtualNatsStarter: SpringVirtualNatsStarter

    @MockitoBean
    private lateinit var innlesingService: InnlesingService

    @Test
    suspend fun `postUtendoersBruk kaller videre paa innsendingService`() {
        // Given:
        val utendoersBrukDtoMock = createUtendoersBrukDtoMock()

        // When:
        webTestClient.post()
            .uri("/plantevernjournal/innlesing/v1/utendoersbruk")
            .acceptCharset(StandardCharsets.UTF_8)
            .bodyValue(utendoersBrukDtoMock)
            .exchange()
            .expectStatus().isCreated

        // Then:
        verify(innlesingService).postUtendoersBruk(
            innsender = anyOrNull(),
            paaVegneAv = anyOrNull(),
            utendoersBrukDto = any(),
        )
    }

    @Test
    suspend fun `postInnendoersBruk kaller videre paa innsendingService`() {
        // Given:
        val innendoersBrukDtoMock = createInnendoersBrukDtoMock()

        // When:
        webTestClient.post()
            .uri("/plantevernjournal/innlesing/v1/innendoersbruk")
            .acceptCharset(StandardCharsets.UTF_8)
            .bodyValue(innendoersBrukDtoMock)
            .exchange()
            .expectStatus().isCreated

        // Then:
        verify(innlesingService, times(1)).postInnendoersBruk(
            innendoersBrukDto = any(),
            innsender = anyOrNull(),
            paaVegneAv = anyOrNull(),
        )
    }

    @Test
    suspend fun `postFroeEllerFormeringsMateriale kaller videre paa innsendingService`() {
        // Given:
        val froeEllerFormeringsMatrialeDtoMock = createFroeEllerFormeringsMaterialeDtoMock()

        // When:
        webTestClient.post()
            .uri("/plantevernjournal/innlesing/v1/formeringsmateriale")
            .acceptCharset(StandardCharsets.UTF_8)
            .bodyValue(froeEllerFormeringsMatrialeDtoMock)
            .exchange()
            .expectStatus().isCreated

        // Then:
        verify(innlesingService).postFroeEllerFormeringsMateriale(
            froeEllerFormeringsMatrialeDto = any(),
            innsender = anyOrNull(),
            paaVegneAv = anyOrNull(),
        )
    }

    @Test
    fun `deleteUtendoersBruk kaller videre paa innsendingService`() {
        // Given:
        val id = UUID.randomUUID()

        // When:
        webTestClient.delete()
            .uri("/plantevernjournal/innlesing/v1/utendoersbruk/$id")
            .acceptCharset(StandardCharsets.UTF_8)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent

        // Then:
        verify(innlesingService).deleteUtendoersBruk(id = id, innsender = null, paaVegneAv = null)
    }

    @Test
    fun `deleteInnendoersBruk kaller videre paa innsendingService`() {
        // Given:
        val id = UUID.randomUUID()

        // When:
        webTestClient.delete()
            .uri("/plantevernjournal/innlesing/v1/innendoersbruk/$id")
            .acceptCharset(StandardCharsets.UTF_8)
            .exchange()
            .expectStatus().isNoContent

        // Then:
        verify(innlesingService).deleteInnendoersBruk(id = id, innsender = null, paaVegneAv = null)
    }

    @Test
    fun `deleteFroeEllerFormeringsMatriale kaller videre paa innsendingService`() {
        // Given:
        val id = UUID.randomUUID()

        // When:
        webTestClient.delete()
            .uri("/plantevernjournal/innlesing/v1/formeringsmateriale/$id")
            .acceptCharset(StandardCharsets.UTF_8)
            .exchange()
            .expectStatus().isNoContent

        // Then:
        verify(innlesingService).deleteFroeEllerFormeringsMatriale(id = id, innsender = null, paaVegneAv = null)
    }

}
