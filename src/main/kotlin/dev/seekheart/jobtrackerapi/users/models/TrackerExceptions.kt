package dev.seekheart.jobtrackerapi.users.models

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import java.util.*

class TrackerNameAlreadyExistsException(name: String) : IllegalArgumentException("Tracker name already exists!") {
}

class TrackerNotFoundException(id: UUID) : NotFoundException() {
    override val message: String = "Tracker with id $id not found"
}