package no.mattilsynet.plantevernjournal.api.errorhandling

import com.fasterxml.jackson.databind.JsonMappingException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import no.mattilsynet.plantevernjournal.api.controllers.models.FeilmeldingModellDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class FeilhaandteringControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<FeilmeldingModellDto> =
        ResponseEntity(
            FeilmeldingModellDto(
                melding = ex.message,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST,
        )

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<FeilmeldingModellDto> =
        ResponseEntity(
            FeilmeldingModellDto(
                melding = ex.message,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST,
        )

    @ExceptionHandler(JsonMappingException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request - Invalid JSON",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleJsonMappingException(ex: JsonMappingException): ResponseEntity<FeilmeldingModellDto> {
        val melding = sjekkUgyldigGeometriType(ex.message) ?: when {
            ex.message?.contains("org.wololo.geojson.Feature", ignoreCase = true) == true ->
            "Ugyldig GeoJSON Feature: Sjekk at strukturen følger GeoJSON-standarden."

            ex.message?.contains("org.wololo.geojson.FeatureCollection", ignoreCase = true) == true ->
            "Ugyldig GeoJSON FeatureCollection: Sjekk at 'features' er en array med gyldige Feature-objekter."

            else -> "Det skjedde en feil med deserialisering av json: ${ex.message}"
        }

        return ResponseEntity(
            FeilmeldingModellDto(
                melding = melding,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    private fun sjekkUgyldigGeometriType(melding: String?): String? {
        val geometriTyper = listOf("LineString", "MultiLineString", "MultiPoint", "MultiPolygon", "Point", "Polygon")

        return geometriTyper.firstOrNull { type ->
            melding?.contains("$type[\"coordinates\"]", ignoreCase = true) == true
        }?.let { type ->
            "Ugyldig GeoJSON: Geometritypen $type stemmer ikke overens med koordinatstrukturen"
        }
    }
}
