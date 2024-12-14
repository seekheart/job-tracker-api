package dev.seekheart.jobtrackerapi.trackers

import dev.seekheart.jobtrackerapi.trackers.models.Tracker
import dev.seekheart.jobtrackerapi.trackers.repositories.TrackerRepository
import dev.seekheart.jobtrackerapi.trackers.services.TrackerService
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    fun test_getTrackersbyUserId_should_return_trackers() {
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
}