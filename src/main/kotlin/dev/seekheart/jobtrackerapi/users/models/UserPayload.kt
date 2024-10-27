package dev.seekheart.jobtrackerapi.users.models

import java.util.*

data class UserPayload(var id: UUID?, val name: String)

fun User.toPayload(): UserPayload {
    return UserPayload(id = id, name = name)
}