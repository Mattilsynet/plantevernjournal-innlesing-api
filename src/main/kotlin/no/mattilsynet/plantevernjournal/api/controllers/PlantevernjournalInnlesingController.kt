package no.mattilsynet.plantevernjournal.api.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import kotlinx.serialization.ExperimentalSerializationApi
import no.mattilsynet.plantevernjournal.api.controllers.models.FroeEllerFormeringsMatrialeDto
import no.mattilsynet.plantevernjournal.api.controllers.models.InnendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.UtendoersBrukDto
import no.mattilsynet.plantevernjournal.api.controllers.models.jwt.AuthorizationDetail
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.FroeEllerFormeringsMatrialeResponsDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.InnendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.controllers.models.responses.UtendoersBrukResponsDto
import no.mattilsynet.plantevernjournal.api.services.InnlesingService
import no.mattilsynet.plantevernjournal.api.validation.FeatureCollectionValidator
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
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
    private val featureCollectionValidator: FeatureCollectionValidator,
    private val innlesingService: InnlesingService,
) {

    @PostMapping("/formeringsmateriale", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting på frø og formeringsmateriale",
    )
    fun postFroeEllerFormeringsmateriale(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "froeEllerFormeringsMatrialeDto",
            description = "Data for å plantevernjournal for formeringsmateriale eller frø"
        ) @Valid @RequestBody froeEllerFormeringsMatrialeDto: FroeEllerFormeringsMatrialeDto,
    ): ResponseEntity<FroeEllerFormeringsMatrialeResponsDto> =
        froeEllerFormeringsMatrialeDto.behandledeOmraader?.let { featureCollection ->
            featureCollectionValidator.validate(featureCollection)
        }.run {
            innlesingService.postFroeEllerFormeringsMatriale(
                froeEllerFormeringsMatrialeDto = froeEllerFormeringsMatrialeDto,
                innsender = jwt?.getInnsenderFraTokenEllerNull(),
                paaVegneAv = jwt?.getPaaVegneAvFraToken(),
            ).let { froeEllerFormeringsMatrialeResponsDto ->
                return ResponseEntity.status(HttpStatus.CREATED).body(froeEllerFormeringsMatrialeResponsDto)
            }
        }

    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting som foregår innendørs, feks i et veksthus",
    )
    @PostMapping("/innendoersbruk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postInnendoersBruk(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "innendoersBrukDto",
            description = "Data for å plantevernjournal for innendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody innendoersBrukDto: InnendoersBrukDto,
    ): ResponseEntity<InnendoersBrukResponsDto> =
        innendoersBrukDto.behandledeOmraader?.let { featureCollection ->
            featureCollectionValidator.validate(featureCollection)
        }.run {
            innlesingService.postInnendoersBruk(
                innendoersBrukDto = innendoersBrukDto,
                innsender = jwt?.getInnsenderFraTokenEllerNull(),
                paaVegneAv = jwt?.getPaaVegneAvFraToken(),
            ).let { innendoersBrukResponsDto ->
                return ResponseEntity.status(HttpStatus.CREATED).body(innendoersBrukResponsDto)
            }
        }

    @Operation(
        description = "Endepunkt for å sende inn informasjon om sprøyting som skjer utendørs, feks på et jorde",
    )
    @PostMapping("/utendoersbruk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postUtendoersBruk(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "utendoersBrukDto",
            description = "Data for å plantevernjournal for utendørs bruk av plantevernmiddel"
        ) @Valid @RequestBody utendoersBrukDto: UtendoersBrukDto,
    ): ResponseEntity<UtendoersBrukResponsDto> =
        featureCollectionValidator.validate(
            utendoersBrukDto.behandledeOmraader,
        ).run {
            innlesingService.postUtendoersBruk(
                innsender = jwt?.getInnsenderFraTokenEllerNull(),
                paaVegneAv = jwt?.getPaaVegneAvFraToken(),
                utendoersBrukDto = utendoersBrukDto,
            ).let { utendoersBrukResponsDto ->
                return ResponseEntity.status(HttpStatus.CREATED).body(utendoersBrukResponsDto)
            }
        }

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om utendørs sprøyting",
    )
    @DeleteMapping("/utendoersbruk/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUtendoersBruk(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteUtendoersBruk(
            id = id,
            innsender = jwt?.getInnsenderFraTokenEllerNull(),
            paaVegneAv = jwt?.getPaaVegneAvFraToken(),
        )
        return ResponseEntity.noContent().build()
    }.getOrThrow()

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om innendørs sprøyting",
    )
    @DeleteMapping("/innendoersbruk/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteInnendoersBruk(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteInnendoersBruk(
            id = id,
            innsender = jwt?.getInnsenderFraTokenEllerNull(),
            paaVegneAv = jwt?.getPaaVegneAvFraToken(),
        )
        return ResponseEntity.noContent().build()
    }.onFailure {
        throw it
    }.getOrDefault(ResponseEntity.noContent().build())

    @Operation(
        description = "Endepunkt for å slette informasjon som er sendt inn om sprøyting av frø og formeringmateriale",
    )
    @DeleteMapping("/formeringsmateriale/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteFroeEllerFormeringsMateriale(
        @AuthenticationPrincipal jwt: Jwt?,
        @Parameter(
            name = "id",
            description = "Id til innlesingen som skal slettes"
        ) @PathVariable id: UUID,
    ): ResponseEntity<Unit> = runCatching {
        innlesingService.deleteFroeEllerFormeringsMatriale(
            id = id,
            innsender = jwt?.getInnsenderFraTokenEllerNull(),
            paaVegneAv = jwt?.getPaaVegneAvFraToken(),
        )
        return ResponseEntity.noContent().build()
    }.onFailure {
        throw it
    }.getOrDefault(ResponseEntity.noContent().build())

    private fun Jwt.getInnsenderFraTokenEllerNull() =
        getClaimAsMap("consumer")
            ?.get("ID")
            ?.let {
                it as String
            }?.substringAfter(':')

    private val objectMapper = jacksonObjectMapper()

    private fun Jwt.getPaaVegneAvFraToken() =
        (claims["authorization_details"]?.let {
            objectMapper.convertValue(it, object : TypeReference<List<AuthorizationDetail>>() {})
        }[0]?.systemUserOrg?.let { id as String })?.substringAfter(":")

}
