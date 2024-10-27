package dev.seekheart.jobtrackerapi.users.exceptions

class UserAlreadyExistsException(name: String) : IllegalArgumentException("User name = $name already exists!") {
}