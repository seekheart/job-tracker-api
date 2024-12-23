package dev.seekheart.jobtrackerapi.trackers

import dev.seekheart.jobtrackerapi.trackers.models.*
import dev.seekheart.jobtrackerapi.trackers.repositories.TrackerRepository
import dev.seekheart.jobtrackerapi.trackers.services.TrackerService
import dev.seekheart.jobtrackerapi.users.models.UserNotFoundException
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class TrackerServiceTests {
    @InjectMocks
    private lateinit var trackerService: TrackerService

    @Mock
    lateinit var trackerRepository: TrackerRepository

    @Mock
    lateinit var userRepository: UserRepository


    @Test
    fun test_getTrackersByUserId_should_return_trackers() {
        val userId = UUID.randomUUID()
        val trackers = listOf<Tracker>(
            Tracker(owner = userId, name = "test a"),
            Tracker(owner = userId, name = "test b"),
            Tracker(owner = userId, name = "test c"),
        )

        `when`(trackerRepository.findAllByOwner(userId)).thenReturn(trackers)

        val result = trackerService.getTrackersByUserId(userId)
        assertEquals(trackers.size, result.size)
    }

    @Test
    fun test_saveTrackerToUser_should_return_tracker_attached_to_user() {
        val userId = UUID.randomUUID()
        val tracker = Tracker(owner = userId, name = "test a")
        val payload = tracker.toPayload()


        `when`(userRepository.existsById(userId)).thenReturn(true)
        `when`(trackerRepository.existsByName("test a")).thenReturn(false)
        `when`(trackerRepository.save(tracker)).thenReturn(tracker)

        val result = trackerService.saveTrackerToUser(userId, payload)

        assertEquals(result, payload)
    }

    @Test
    fun test_saveTrackerToUser_should_throw_UserNotFoundException() {
        val userId = UUID.randomUUID()
        val tracker = Tracker(owner = userId, name = "test a")
        val payload = tracker.toPayload()

        `when`(userRepository.existsById(userId)).thenReturn(false)

        assertThrows<UserNotFoundException> {
            trackerService.saveTrackerToUser(userId, payload)
        }
    }

    @Test
    fun test_saveTrackerToUser_should_throw_TrackerNameAlreadyExistsException() {
        val userId = UUID.randomUUID()
        val tracker = Tracker(owner = userId, name = "test a")
        val payload = tracker.toPayload()

        `when`(userRepository.existsById(userId)).thenReturn(true)
        `when`(trackerRepository.existsByName("test a")).thenReturn(true)

        assertThrows<TrackerNameAlreadyExistsException> {
            trackerService.saveTrackerToUser(userId, payload)
        }
    }

    @Test
    fun test_updateTracker_should_return_modified_tracker() {
        val userId = UUID.randomUUID()
        val tracker = Tracker(owner = userId, name = "test a")
        val payload = TrackerPayload(id = tracker.id, user = userId, name = "updated test a")

        `when`(trackerRepository.findById(tracker.id)).thenReturn(Optional.of(tracker))
        `when`(trackerRepository.save(payload.toTracker(tracker.owner)))
            .thenReturn(payload.toTracker(tracker.owner))

        val result = trackerService.updateTracker(tracker.id, payload)

        assertEquals(payload, result)
    }
}