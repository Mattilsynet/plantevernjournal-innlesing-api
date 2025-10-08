package no.mattilsynet.plantevernjournal.api.errorhandling

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

}
