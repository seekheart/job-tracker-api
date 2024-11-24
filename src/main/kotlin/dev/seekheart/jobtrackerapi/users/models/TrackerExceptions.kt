package dev.seekheart.jobtrackerapi.users.models

class TrackerNameAlreadyExistsException(name: String) : IllegalArgumentException("Tracker name already exists!") {
}