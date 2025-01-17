package dev.seekheart.jobtrackerapi.trackers.models

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import java.util.*

class TrackerNameAlreadyExistsException(name: String) :
    IllegalArgumentException("Tracker name = $name already exists!") {
}

class TrackerNotFoundException(id: UUID) : NotFoundException() {
    override val message: String = "Tracker with id $id not found"
}