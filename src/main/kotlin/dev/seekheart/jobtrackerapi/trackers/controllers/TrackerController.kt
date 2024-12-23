package dev.seekheart.jobtrackerapi.trackers.controllers

import dev.seekheart.jobtrackerapi.jobs.models.JobPayload
import dev.seekheart.jobtrackerapi.jobs.services.JobService
import dev.seekheart.jobtrackerapi.trackers.models.TrackerPayload
import dev.seekheart.jobtrackerapi.trackers.services.TrackerService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/trackers"])
class TrackerController(val trackerService: TrackerService, val jobService: JobService) {
    val logger = LoggerFactory.getLogger(TrackerController::class.java)

    @PutMapping("/{id}")
    fun updateTracker(@PathVariable id: UUID, @RequestBody request: TrackerPayload): ResponseEntity<Unit> {
        trackerService.updateTracker(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTracker(@PathVariable id: UUID): ResponseEntity<Unit> {
        trackerService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/jobs")
    fun getJobsByTracker(@PathVariable id: UUID): List<JobPayload> {
        return jobService.findAllByTracker(id)
    }

    @PostMapping("/{id}/jobs")
    fun saveJobToTracker(@PathVariable id: UUID, @RequestBody jobPayload: JobPayload): ResponseEntity<JobPayload> {
        val response = jobService.saveJobToTracker(jobPayload)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}