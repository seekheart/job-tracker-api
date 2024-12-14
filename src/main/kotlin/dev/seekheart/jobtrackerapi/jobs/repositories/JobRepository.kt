package dev.seekheart.jobtrackerapi.jobs.repositories

import dev.seekheart.jobtrackerapi.jobs.models.Job
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JobRepository : CrudRepository<Job, UUID> {
    fun findAllByTrackerId(trackerId: UUID): List<Job>
}