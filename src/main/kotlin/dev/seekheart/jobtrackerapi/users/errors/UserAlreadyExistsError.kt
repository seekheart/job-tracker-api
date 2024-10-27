package dev.seekheart.jobtrackerapi.users.errors

class UserAlreadyExistsError(name: String) : IllegalArgumentException("User name = $name already exists!") {
}