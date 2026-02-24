package no.mattilsynet.plantevernjournal.api.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.FroeEllerFormeringsMatrialeResponsDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.InnendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.UtendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.services.InnlesingService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@kotlin.uuid.ExperimentalUuidApi
@RestController
@RequestMapping("plantevernjournal/innlesing/v1")
@Tag(
    description = "Endepunkter for å lese inn plantevernjournaler",
    name = "Innlesing plantevernjournal",
)
class PlantevernjournalInnlesingController(
    private val innlesingService: InnlesingService,
) {

    @PostMapping("/formeringsmateriale", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting på frø og formeringsmateriale",
    )
    fun postFroeEllerFormeringsmateriale(
        @Parameter(
            name = "froeEllerFormeringsMatrialeDto",
            description = "Data for å plantevernjournal for formeringsmateriale eller frø"
        ) @Valid @RequestBody froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto,
    ): ResponseEntity<FroeEllerFormeringsMatrialeResponsDto> =
        runCatching {
            innlesingService.postFroeEllerFormeringsMatriale(froeEllerFormeringsMatrialeDto)
                .let { froeEllerFormeringsMatrialeResponsDto ->
                    return ResponseEntity.status(HttpStatus.CREATED).body(froeEllerFormeringsMatrialeResponsDto)
                }
        }.getOrThrow()

    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting som foregår innendørs, feks i et veksthus",
    )
    @PostMapping("/innendoersbruk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postInnendoersBruk(
        @Parameter(
            name = "innendoersBrukDto",
            description = "Data for å plantevernjournal for innendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody innendoersBrukDto: InnendoersBrukDto,
    ): ResponseEntity<InnendoersBrukResponsDto> = runCatching {
        innlesingService.postInnendoersBruk(innendoersBrukDto = innendoersBrukDto)
            .let { innendoersBrukResponsDto ->
                return ResponseEntity.status(HttpStatus.CREATED).body(innendoersBrukResponsDto)
            }
    }.getOrThrow()

    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting som skjer utendørs, feks på et jorde",
    )
    @PostMapping("/utendoersbruk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postUtendoersBruk(
        @Parameter(
            name = "utendoersBrukDto",
            description = "Data for å plantevernjournal for utendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody utendoersBrukDto: UtendoersBrukDto,
    ): ResponseEntity<UtendoersBrukResponsDto> = runCatching {
        innlesingService.postUtendoersBruk(utendoersBrukDto = utendoersBrukDto)
            .let { utendoersBrukResponsDto ->
                return ResponseEntity.status(HttpStatus.CREATED).body(utendoersBrukResponsDto)
            }
    }.getOrThrow()

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om utendørs sprøyting",
    )
    @DeleteMapping("/utendoersbruk/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUtendoersBruk(
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteUtendoersBruk(id)
        return ResponseEntity.noContent().build()
    }.getOrThrow()

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om innendørs sprøyting",
    )
    @DeleteMapping("/innendoersbruk/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteInnendoersBruk(
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteInnendoersBruk(id)
        return ResponseEntity.noContent().build()
    }.onFailure {
        throw it
    }.getOrDefault(ResponseEntity.noContent().build())

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om sprøyting av frø og formeringmateriale",
    )
    @DeleteMapping("/formeringsmateriale/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteFroeEllerFormeringsMateriale(
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteFroeEllerFormeringsMatriale(id)
        return ResponseEntity.noContent().build()
    }.onFailure {
        throw it
    }.getOrDefault(ResponseEntity.noContent().build())
}
