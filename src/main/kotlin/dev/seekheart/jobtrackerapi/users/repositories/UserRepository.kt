package dev.seekheart.jobtrackerapi.users.repositories

import dev.seekheart.jobtrackerapi.users.models.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, UUID> {
    fun findByName(name: String): User?
    fun findByEmail(email: String): User?
    fun existsByToken(token: String): Boolean
    fun findByToken(token: String): User?
}