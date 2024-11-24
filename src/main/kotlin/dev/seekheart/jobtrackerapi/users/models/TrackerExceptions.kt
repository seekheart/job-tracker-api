package dev.seekheart.jobtrackerapi.users.models

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class TrackerNameAlreadyExistsException(name: String) : IllegalArgumentException("Tracker name already exists!") {
}