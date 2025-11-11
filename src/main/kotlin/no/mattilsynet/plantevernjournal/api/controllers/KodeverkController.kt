package no.mattilsynet.plantevernjournal.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import no.mattilsynet.plantevernjournal.api.controllers.models.KodeDto
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Bruksomraade
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.Enhet
import no.mattilsynet.plantevernjournal.api.shared.kodeverk.GeometriTyper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("plantevernjournal/kodeverk/v1")
@Tag(
    description = "Endepunkter for å hente kodeverk fra plantevernjournal-innlesing-api",
    name = "Kodeverk plantevernjournal",
)
class KodeverkController {

    @GetMapping("/geometritype")
    @Operation(
        description = "Endepunkt for å hente alle geometrityper",
    )
    fun getGeometrityper()
    : List<KodeDto> =
        GeometriTyper.entries.map {
            KodeDto(
                beskrivelse = it.beskrivelse,
                kode = it.name,
            )
        }

    @GetMapping("/bruksomraade")
    @Operation(
        description = "Endepunkt for å hente alle bruksområder",
    )
    fun getBruksomraader()
    : List<KodeDto> =
        Bruksomraade.entries.map {
            KodeDto(
                beskrivelse = it.beskrivelse,
                kode = it.name,
            )
        }

    @GetMapping("/enhet")
    @Operation(
        description = "Endepunkt for å hente alle enheter",
    )
    fun getEnheter()
    : List<KodeDto> =
        Enhet.entries.map {
            KodeDto(
                beskrivelse = it.enhet,
                kode = it.name,
            )
        }
}
