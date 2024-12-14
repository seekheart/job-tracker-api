package dev.seekheart.jobtrackerapi.trackers.models

import java.util.*

data class TrackerPayload(
    var id: UUID?,
    val user: UUID,
    var name: String,
)

fun Tracker.toPayload(): TrackerPayload {
    return TrackerPayload(id = id, user = owner, name = name)
}