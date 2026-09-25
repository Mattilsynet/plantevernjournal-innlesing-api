package no.mattilsynet.plantevernjournal.api.services

import kotlinx.coroutines.runBlocking
import no.mattilsynet.plantevernjournal.api.clients.EppoApiClient
import no.mattilsynet.plantevernjournal.api.clients.models.EppoTaxon
import no.mattilsynet.plantevernjournal.api.mocks.domain.EppoTaxonMocker.createEppoTaxonMock
import no.mattilsynet.plantevernjournal.api.nats.consumers.EppoKvConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.models.EppoNats
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

internal class EppoServiceTest {

    private lateinit var eppoService: EppoService

    private val eppoApiClient = mock(EppoApiClient::class.java)
    private val eppoKvConsumer = mock(EppoKvConsumer::class.java)

    private val eppoKode = "eppokode"

    @BeforeEach
    fun setUp() {
        eppoService = EppoService(
            eppoApiClient = eppoApiClient,
            eppoKvConsumer = eppoKvConsumer,
        )

    }

    @Test
    suspend fun `getNavnFraEppoKode returnerer plantenavn fra kvBucket`() {
        // Given:
        doReturn("Plantenavn").`when`(eppoKvConsumer).getEppoNavnFraNats(eppoKode = eppoKode)

        // When & then:
        assertEquals(
            "Plantenavn",
            eppoService.getNavnFraEppoKode(eppoKode = eppoKode)
        )

        verify(eppoApiClient, times(0)).getNavnFraEppoKode(any())
    }

    @Test
    suspend fun `getNavnFraEppoKode returnerer plantenavn fra eppoApiClient`() {
        // Given:
        doReturn(null).`when`(eppoKvConsumer).getEppoNavnFraNats(eppoKode = eppoKode)
        doReturn(
            listOf(createEppoTaxonMock())
        ).`when`(eppoApiClient).getNavnFraEppoKode(eppoKode = eppoKode)

        // When & then:
        assertEquals(
            "Plantenavnet",
            eppoService.getNavnFraEppoKode(eppoKode = eppoKode)
        )

        verify(eppoApiClient, times(1)).getNavnFraEppoKode(eppoKode = eppoKode)
        verify(eppoKvConsumer, times(1)).getEppoNavnFraNats(eppoKode = eppoKode)
        verify(eppoKvConsumer, times(1))
            .putEppoTilNats(EppoNats(eppoKode = eppoKode, eppoNavn = "Plantenavnet"))
    }

    @Test
    suspend fun `getNavnFraEppoKode kaster feil naar level er for lav`() {
        // Given:
        doReturn(null).`when`(eppoKvConsumer).getEppoNavnFraNats(eppoKode = eppoKode)
        doReturn(
            listOf(
                createEppoTaxonMock(
                    level = 2,
                    type = "Class",
                )
            )
        ).`when`(eppoApiClient).getNavnFraEppoKode(eppoKode = eppoKode)

        // When & then:
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking {
                eppoService.getNavnFraEppoKode(eppoKode = eppoKode)
            }
        }.message!!.let { message ->
            Assertions.assertEquals(
                "Eppokoden $eppoKode har nivå 2 (Class), som er lavere enn 7 (Genus)",
                message
            )
        }

        verify(eppoApiClient, times(1)).getNavnFraEppoKode(eppoKode = eppoKode)
        verify(eppoKvConsumer, times(1)).getEppoNavnFraNats(eppoKode = eppoKode)
    }

    @Test
    suspend fun `getNavnFraEppoKode returnerer null naar navn ikke finnes`() {
        // Given:
        doReturn(null).`when`(eppoKvConsumer).getEppoNavnFraNats(eppoKode = eppoKode)
        doReturn(emptyList<EppoTaxon>()).`when`(eppoApiClient).getNavnFraEppoKode(eppoKode = eppoKode)

        // When & then:
        assertNull(eppoService.getNavnFraEppoKode(eppoKode = eppoKode))

        verify(eppoApiClient, times(1)).getNavnFraEppoKode(eppoKode = eppoKode)
        verify(eppoKvConsumer, times(1))
    }

}
