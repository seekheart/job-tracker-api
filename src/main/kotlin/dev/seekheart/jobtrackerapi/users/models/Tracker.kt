package dev.seekheart.jobtrackerapi.users.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Tracker(
    @Id
    val id: UUID = UUID.randomUUID(),
    val owner: UUID,
    var name: String
)

fun TrackerPayload.toTracker(userId: UUID): Tracker {
    return Tracker(id = id ?: UUID.randomUUID(), owner = userId, name = name)
}
