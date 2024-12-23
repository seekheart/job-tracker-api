package dev.seekheart.jobtrackerapi.jobs.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Job(
    @Id
    val id: UUID = UUID.randomUUID(),
    var company: String,
    var position: String,
    var salary: Long,
    var description: String,
    var dateApplied: Long,
    var lastUpdated: Long,
    var latestStage: String,
    var didGetOffer: Boolean?,
    val trackerId: UUID
)

fun JobPayload.toJob(): Job {
    return Job(
        id = id ?: UUID.randomUUID(),
        company = company,
        position = position,
        salary = salary,
        description = description,
        dateApplied = dateApplied,
        lastUpdated = lastUpdated,
        latestStage = latestStage,
        didGetOffer = didGetOffer,
        trackerId = trackerId
    )
}