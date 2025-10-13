package no.mattilsynet.plantevernjournal.api

import no.mattilsynet.fisk.libs.spring.virtualnats.SpringVirtualNatsStarter
import no.mattilsynet.fisk.libs.spring.virtualnats.wrapper.LoggerVirtualNatsWrapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    LoggerVirtualNatsWrapper::class,
    SpringVirtualNatsStarter::class,
)
class PlantevernjournalApiApplication

fun main(args: Array<String>) {
	runApplication<PlantevernjournalApiApplication>(*args)
}
