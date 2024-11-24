package dev.seekheart.jobtrackerapi.users.controllers

import dev.seekheart.jobtrackerapi.users.models.TrackerPayload
import dev.seekheart.jobtrackerapi.users.services.TrackerService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/trackers"])
class TrackerController(val trackerService: TrackerService) {
    val logger = LoggerFactory.getLogger(TrackerController::class.java)

    @PatchMapping("/{id}")
    fun updateTracker(@PathVariable id: UUID, @RequestBody request: TrackerPayload) {
        trackerService.updateTracker(id, request)
    }

    @DeleteMapping("/{id}")
    fun deleteTracker(@PathVariable id: UUID): ResponseEntity<Unit> {
        trackerService.delete(id)
        return ResponseEntity.noContent().build()
    }
}