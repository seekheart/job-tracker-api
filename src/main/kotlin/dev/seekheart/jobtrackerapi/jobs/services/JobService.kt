package dev.seekheart.jobtrackerapi.jobs.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.jobs.models.JobNotFoundException
import dev.seekheart.jobtrackerapi.jobs.models.JobPayload
import dev.seekheart.jobtrackerapi.jobs.models.toJob
import dev.seekheart.jobtrackerapi.jobs.models.toPayload
import dev.seekheart.jobtrackerapi.jobs.repositories.JobRepository
import dev.seekheart.jobtrackerapi.trackers.models.TrackerNotFoundException
import dev.seekheart.jobtrackerapi.trackers.services.TrackerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class JobService(val jobRepository: JobRepository, val trackerService: TrackerService, val objectMapper: ObjectMapper) {
    var logger = LoggerFactory.getLogger(JobService::class.java)

    private fun checkIfTrackerExists(trackerId: UUID) {
        if (!trackerService.checkIfExists(trackerId)) {
            throw TrackerNotFoundException(trackerId)
        }
    }

    fun findAllByTracker(trackerId: UUID): List<JobPayload> {
        checkIfTrackerExists(trackerId)
        return jobRepository.findAllByTrackerId(trackerId).map { it.toPayload() }
    }

    fun saveJobToTracker(payload: JobPayload): JobPayload {
        checkIfTrackerExists(payload.trackerId)
        val record = payload.toJob()

        logger.info("Saving job to tracker id = ${record.trackerId}")
        jobRepository.save(record)
        return record.toPayload()
    }

    fun updateJob(jobId: UUID, payload: JobPayload): JobPayload {
        var record = jobRepository.findById(jobId).orElseThrow { JobNotFoundException(jobId) }
        record = objectMapper.updateValue(record, payload.toJob())

        jobRepository.save(record)
        return record.toPayload()

    }

    fun deleteJobById(jobId: UUID) {
        if (jobRepository.existsById(jobId)) {
            jobRepository.deleteById(jobId)
        } else {
            logger.error("Job with ID $jobId not found")
            throw JobNotFoundException(jobId)
        }
    }
}