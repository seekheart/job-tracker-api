package dev.seekheart.jobtrackerapi.users.exceptions

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import java.util.*

class UserNotFoundException(id: UUID) : NotFoundException() {
    override val message: String = "User with id $id not found"
}