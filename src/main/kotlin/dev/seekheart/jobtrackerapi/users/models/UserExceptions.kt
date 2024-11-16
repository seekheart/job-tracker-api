package dev.seekheart.jobtrackerapi.users.models

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import java.util.*

class UserAlreadyExistsException(name: String) : IllegalArgumentException("User name = $name already exists!") {
}

class UserNotFoundException(id: UUID) : NotFoundException() {
    override val message: String = "User with id $id not found"
}

class UserEmailNotFoundException(email: String) : NotFoundException() {
    override val message: String = "User with email $email not found"
}

class InvalidUserCredentialsException : RuntimeException() {
    override val message: String = "User credentials are invalid!"
}