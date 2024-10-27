package dev.seekheart.jobtrackerapi.handlers

import dev.seekheart.jobtrackerapi.users.exceptions.UserAlreadyExistsException
import dev.seekheart.jobtrackerapi.users.exceptions.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalErrorController {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleAlreadyExistsErrors() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundErrors() {
    }
}