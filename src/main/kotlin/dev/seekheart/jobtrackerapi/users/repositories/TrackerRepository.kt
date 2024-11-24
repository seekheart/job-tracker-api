package dev.seekheart.jobtrackerapi.users.repositories

import dev.seekheart.jobtrackerapi.users.models.Tracker
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TrackerRepository : CrudRepository<Tracker, UUID> {
    fun existsByName(name: String): Boolean
    fun findAllByOwner(userId: UUID): List<Tracker>
}