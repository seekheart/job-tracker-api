package dev.seekheart.jobtrackerapi.trackers.repositories

import dev.seekheart.jobtrackerapi.trackers.models.Tracker
import org.springframework.data.repository.CrudRepository
import java.util.*

interface TrackerRepository : CrudRepository<Tracker, UUID> {
    fun existsByName(name: String): Boolean
    fun findAllByOwner(userId: UUID): List<Tracker>
}