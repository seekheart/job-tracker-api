package dev.seekheart.jobtrackerapi.users.models

import java.util.*

data class UserPayload(
    var id: UUID?,
    var name: String,
    var email: String,
    var role: Role
)

fun User.toPayload(): UserPayload {
    return UserPayload(id = id, name = name, email = email, role = role)
}

data class UserRegisterPayload(
    var id: UUID?,
    val name: String,
    val email: String,
    var password: String,
    val role: Role
)

data class UserLoginPayload(val email: String, val password: String)

data class UserTokenPayload(val token: String, val expiration: Long)