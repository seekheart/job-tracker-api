package dev.seekheart.jobtrackerapi.users.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Duration
import java.time.Instant
import java.util.*

enum class Role {
    USER, ADMIN
}

@Entity
@Table(name = "app_user")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    private var password: String,
    var token: String? = null,
    var tokenExpires: Long = Instant.now().plus(Duration.ofHours(1)).toEpochMilli(),
    val role: Role = Role.USER,
) : UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.toString()))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }
}

fun UserPayload.toUser(password: String): User {
    return User(id = id ?: UUID.randomUUID(), name = name, email = email, password = password)
}

fun UserRegisterPayload.toUser(): User {
    return User(id = id ?: UUID.randomUUID(), name = name, email = email, password = password, role = role)
}

