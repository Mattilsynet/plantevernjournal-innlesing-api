package no.mattilsynet.plantevernjournal_api.controllers

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import no.mattilsynet.plantevernjournal_api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal_api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal_api.controllers.models.UtendoersBrukDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("plantevernjournal/innlesing/v1")
@Tag(
    description = "Endepunkter for å lese inn plantevernjournaler",
    name = "Innlesing plantevernjournal",
)
class PlantevernjournalController {

    @PostMapping("/formeringsmateriale")
    fun postFroeEllerFormeringsmateriale(
        @Parameter(
            name = "froeEllerFormeringsMatrialeDto",
            description = "Data for å plantevernjournal for formeringsmateriale eller frø"
        ) @Valid @RequestBody froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("/innendoersbruk")
    fun postInnendoersBruk(
        @Parameter(
            name = "innendoersBrukDto",
            description = "Data for å plantevernjournal for innendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody innendoersBrukDto: InnendoersBrukDto,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("/utendoersbruk")
    fun postUtendoersBruk(
        @Parameter(
            name = "utendoersBrukDto",
            description = "Data for å plantevernjournal for utendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody utendoersBrukDto: UtendoersBrukDto,
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}
