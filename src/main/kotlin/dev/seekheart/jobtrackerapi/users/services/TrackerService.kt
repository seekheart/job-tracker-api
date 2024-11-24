package dev.seekheart.jobtrackerapi.users.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.users.models.*
import dev.seekheart.jobtrackerapi.users.repositories.TrackerRepository
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class TrackerService(
    val trackerRepository: TrackerRepository,
    val userRepository: UserRepository,
    val objectMapper: ObjectMapper
) {
    val logger = LoggerFactory.getLogger(TrackerService::class.java)

    fun saveTrackerToUser(userId: UUID, tracker: TrackerPayload): TrackerPayload {
        logger.info("Saving tracker to user id = $userId")
        val userExists = userRepository.existsById(userId)
        logger.debug("User id = $userId exists = $userExists")

        if (!userExists) {
            throw UserNotFoundException(userId)
        }

        logger.info("Checking for name duplicate tracker name = ${tracker.name}")
        val trackerExists = trackerRepository.existsByName(tracker.name)

        if (trackerExists) {
            throw TrackerNameAlreadyExistsException(tracker.name)
        }

        val record = tracker.toTracker(userId)

        trackerRepository.save(record)

        return record.toPayload()
    }

    fun getTrackersByUserId(userId: UUID): List<TrackerPayload> {
        val trackers = trackerRepository.findAllByOwner(userId)
        return trackers.map { it.toPayload() }
    }

    fun updateTracker(id: UUID, request: TrackerPayload): TrackerPayload {
        var record = trackerRepository.findById(id).orElseThrow { UserNotFoundException(id) }
        request.id = record.id
        record = objectMapper.updateValue(record, request)
        trackerRepository.save(record)
        return record.toPayload()
    }

    fun delete(id: UUID) {
        if (trackerRepository.existsById(id)) {
            trackerRepository.deleteById(id)
        } else {
            logger.error("Tracker with id $id not found")
            throw TrackerNotFoundException(id)
        }
    }
}