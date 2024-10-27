package dev.seekheart.jobtrackerapi.handlers

import dev.seekheart.jobtrackerapi.users.errors.UserAlreadyExistsError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalErrorController {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsError::class)
    fun handleAlreadyExistsErrors() {

    }
}