package no.mattilsynet.plantevernjournal.api.errorhandling

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import no.mattilsynet.plantevernjournal.api.controllers.models.FeilmeldingModellDto
import org.slf4j.LoggerFactory
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class FeilhaandteringControllerAdvice {

    private val logger = LoggerFactory.getLogger(javaClass)

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
        logger.warn("IllegalArgumentException", ex)
            .run {
                ResponseEntity(
                    FeilmeldingModellDto(
                        melding = ex.message,
                        status = HttpStatus.BAD_REQUEST.value(),
                    ),
                    HttpStatus.BAD_REQUEST,
                )
            }

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
        logger.warn("NoSuchElementException", ex)
            .run {
                ResponseEntity(
                    FeilmeldingModellDto(
                        melding = ex.message,
                        status = HttpStatus.BAD_REQUEST.value(),
                    ),
                    HttpStatus.BAD_REQUEST,
                )
            }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException)
            : ResponseEntity<FeilmeldingModellDto> =
        logger.warn("MethodArgumentNotValidException", ex)
            .run {
                ResponseEntity(
                    FeilmeldingModellDto(
                        melding = ex.message,
                        status = HttpStatus.BAD_REQUEST.value(),
                    ),
                    HttpStatus.BAD_REQUEST,
                )
            }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleWebExchangeBindException(ex: WebExchangeBindException)
            : ResponseEntity<FeilmeldingModellDto> =
        logger.warn("WebExchangeBindException", ex)
            .run {
                ResponseEntity(
                    FeilmeldingModellDto(
                        melding = ex.bindingResult.fieldErrors.map {
                            "${it.field}: ${it.defaultMessage}"
                        }.toList().joinToString(", "),
                        status = HttpStatus.BAD_REQUEST.value(),
                    ),
                    HttpStatus.BAD_REQUEST,
                )
            }

    @ExceptionHandler(ServerWebInputException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [Content(schema = Schema(implementation = FeilmeldingModellDto::class))],
            )]
    )
    fun handleServerWebInputException(ex: ServerWebInputException): ResponseEntity<FeilmeldingModellDto> {
        logger.warn("ServerWebInputException", ex)
        return ResponseEntity(
            FeilmeldingModellDto(
                melding = getFeilmelding(ex),
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    private fun getFeilmelding(exception: Exception) =
        when (exception.cause) {
            is DecodingException ->
                "${exception.cause?.message}"

            else -> "Det skjedde noe feil " + exception.cause?.message
        }
}
