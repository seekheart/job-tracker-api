package dev.seekheart.jobtrackerapi.users

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.users.exceptions.UserNotFoundException
import dev.seekheart.jobtrackerapi.users.models.User
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import dev.seekheart.jobtrackerapi.users.services.UserService
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
class UserServiceTests {
    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var objectMapper: ObjectMapper

    @Test
    fun test_findAll_should_return_users() {
        val users = listOf(User(name = "test"), User(name = "test2"))

        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.findAll()

        assertEquals(result.size, users.size)
        assertEquals(result.get(0).name, users[0].name)
        assertEquals(result.get(0).id, users[0].id)
    }

    @Test
    fun test_findOne_should_return_correct_user() {
        val targetId = UUID.randomUUID()
        val user = User(id = targetId, name = "dino")

        `when`(userRepository.findById(targetId)).thenReturn(Optional.of(user))
        val result = userService.findById(targetId)

        assertEquals(result.id, targetId)
        assertEquals(result.name, "dino")
    }

    @Test
    fun test_findOne_should_throw_exception_userNotFound() {
        val targetId = UUID.randomUUID()
        `when`(userRepository.findById(targetId)).thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> {
            userService.findById(targetId)
        }
    }

}