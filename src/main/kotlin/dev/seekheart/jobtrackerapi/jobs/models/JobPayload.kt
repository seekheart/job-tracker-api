package dev.seekheart.jobtrackerapi.jobs.models

import java.util.*

data class JobPayload(
    var id: UUID?,
    var company: String,
    var position: String,
    var salary: Long,
    var description: String,
    var dateApplied: Long,
    var lastUpdated: Long,
    var latestStage: String,
    var didGetOffer: Boolean?,
    val trackerId: UUID,
)

fun Job.toPayload(): JobPayload {
    return JobPayload(
        id = id,
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
