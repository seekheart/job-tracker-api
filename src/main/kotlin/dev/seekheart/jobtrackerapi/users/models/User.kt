package dev.seekheart.jobtrackerapi.users.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "app_user")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),
    var name: String,
)

fun UserPayload.toUser(): User {
    return User(id = id ?: UUID.randomUUID(), name = name)
}
