package dev.seekheart.jobtrackerapi.handlers

import dev.seekheart.jobtrackerapi.jobs.models.JobNotFoundException
import dev.seekheart.jobtrackerapi.trackers.models.TrackerNameAlreadyExistsException
import dev.seekheart.jobtrackerapi.trackers.models.TrackerNotFoundException
import dev.seekheart.jobtrackerapi.users.models.UserAlreadyExistsException
import dev.seekheart.jobtrackerapi.users.models.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalErrorController {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException::class, TrackerNameAlreadyExistsException::class)
    fun handleAlreadyExistsErrors() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class, TrackerNotFoundException::class, JobNotFoundException::class)
    fun handleUserNotFoundErrors() {
    }
}