package no.mattilsynet.plantevernjournal.api

import no.mattilsynet.fisk.libs.virtualnats.VirtualNats
import no.mattilsynet.plantevernjournal.api.nats.consumers.FroeEllerFormeringsMaterialePushConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.InnendoersPushConsumer
import no.mattilsynet.plantevernjournal.api.nats.consumers.UtendoersPushConsumer
import no.mattilsynet.plantevernjournal.api.services.NatsService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@kotlin.uuid.ExperimentalUuidApi
@kotlinx.serialization.ExperimentalSerializationApi
@Suppress("UnusedPrivateMember")
class PlantevernjournalApiApplicationTests {

    @MockitoBean
    private lateinit var froeEllerFormeringsMaterialePushConsumer: FroeEllerFormeringsMaterialePushConsumer

    @MockitoBean
    private lateinit var innendoersPushConsumer: InnendoersPushConsumer

    @MockitoBean
    private lateinit var utendoersPushConsumer: UtendoersPushConsumer

    @MockitoBean
    private lateinit var natsService: NatsService

    @MockitoBean
    private lateinit var nats: VirtualNats

	@Test
    @Suppress("EmptyFunctionBlock")
	fun contextLoads() {
	}

}
