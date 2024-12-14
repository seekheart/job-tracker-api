package dev.seekheart.jobtrackerapi.jobs.controllers

import dev.seekheart.jobtrackerapi.jobs.models.JobPayload
import dev.seekheart.jobtrackerapi.jobs.services.JobService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/jobs")
class JobController(val jobService: JobService) {
    @PutMapping("/{jobId}")
    fun updateJob(@PathVariable jobId: UUID, @RequestBody jobPayload: JobPayload): ResponseEntity<Unit> {
        jobService.updateJob(jobId, jobPayload)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{jobId}")
    fun deleteJob(@PathVariable jobId: UUID) {
        jobService.deleteJobById(jobId)
    }
}