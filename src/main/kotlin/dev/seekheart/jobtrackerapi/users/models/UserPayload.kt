package dev.seekheart.jobtrackerapi.users.models

import java.util.*

data class UserPayload(var id: UUID = UUID.randomUUID(), val name: String)