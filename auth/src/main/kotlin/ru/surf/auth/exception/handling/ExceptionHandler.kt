package ru.surf.auth.exception.handling

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.surf.auth.exception.ConflictException
import ru.surf.auth.exception.RequestException
import ru.surf.auth.exception.UnauthorizedException


@ControllerAdvice
class ExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java.name)

    @ExceptionHandler(RequestException::class, ConflictException::class, UnauthorizedException::class)
    fun handleClientExceptions(exception: Exception): ResponseEntity<Error> =
            ResponseEntity<Error>(Error(exception::class.simpleName, exception.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(UnknownError::class)
    fun handleServerExceptions(exception: Exception): ResponseEntity<Error> =
            ResponseEntity<Error>(Error(exception::class.simpleName, exception.message), HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(Exception::class)
    fun handleOtherExceptions(exception: Exception): ResponseEntity<Error> {
        logger.error("Unknown error", exception)
        return ResponseEntity<Error>(Error("unknown error", "something went wrong"),
                HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
