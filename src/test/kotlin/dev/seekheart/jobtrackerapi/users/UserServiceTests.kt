package dev.seekheart.jobtrackerapi.users

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.users.models.*
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import dev.seekheart.jobtrackerapi.users.services.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Duration
import java.time.Instant
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
        val users = listOf(
            User(name = "test", email = "test@test.com", password = "abc"),
            User(name = "test2", email = "test2@test.com", password = "abc2")
        )

        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.findAll()

        assertEquals(result.size, users.size)
        assertEquals(result.get(0).name, users[0].name)
        assertEquals(result.get(0).id, users[0].id)
    }

    @Test
    fun test_findOne_should_return_correct_user() {
        val targetId = UUID.randomUUID()
        val user = User(id = targetId, name = "dino", email = "dinino@test.com", password = "abc")

        `when`(userRepository.findById(targetId)).thenReturn(Optional.of(user))
        val result = userService.findById(targetId)

        assertEquals(result.id, targetId)
        assertEquals(result.name, "dino")
    }

    @Test
    fun test_findOne_should_throw_exception_UserNotFoundException() {
        val targetId = UUID.randomUUID()

        assertThrows<UserNotFoundException> {
            userService.findById(targetId)
        }
    }

    @Test
    fun test_validateUserLogin_should_validate_correctly() {
        val passwordEncoder = BCryptPasswordEncoder()
        val testPayload = UserLoginPayload("test@test.com", "abc")

        `when`(userRepository.findByEmail("test@test.com"))
            .thenReturn(
                User(name = "test", email = testPayload.email, password = passwordEncoder.encode(testPayload.password))
            )

        val result = userService.validateUserLogin(testPayload)

        assertEquals(result, true)
    }

    @Test
    fun test_validateUserLogin_should_throw_UserEmailNotFoundException() {
        val testPayload = UserLoginPayload("test@test.com", "abc")

        assertThrows<UserEmailNotFoundException> {
            userService.validateUserLogin(testPayload)
        }
    }

    @Test
    fun test_isValidate_token_should_return_true() {
        val token = "test"
        val user = User(
            name = "test",
            email = "test@test.com",
            password = "abc",
            token = token,
            tokenExpires = Instant.now().plus(Duration.ofMinutes(10)).toEpochMilli()
        )

        `when`(userRepository.existsByToken(token)).thenReturn(true)
        `when`(userRepository.findByToken(token)).thenReturn(user)

        val result = userService.isValidToken(token)

        assertEquals(result, true)
    }

    @Test
    fun test_isValidate_token_no_token_should_return_false() {
        val token = "test"

        `when`(userRepository.existsByToken(token)).thenReturn(false)

        val result = userService.isValidToken(token)

        assertEquals(result, false)
    }

    @Test
    fun test_isValidate_token_expired_should_return_false() {
        val token = "test"
        val user = User(
            name = "test",
            email = "test@test.com",
            password = "abc",
            token = token,
            tokenExpires = Instant.now().minus(Duration.ofMinutes(10)).toEpochMilli()
        )

        `when`(userRepository.existsByToken(token)).thenReturn(true)
        `when`(userRepository.findByToken(token)).thenReturn(user)

        val result = userService.isValidToken(token)

        assertEquals(result, false)
    }

    @Test
    fun test_generateUserLoginToken_should_return_token_payload() {
        val passwordEncoder = BCryptPasswordEncoder()
        val userLogin = UserLoginPayload("test@test.com", "abc")
        val user = User(
            name = "test",
            email = "test@test.com",
            password = passwordEncoder.encode("abc")
        )


        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(userRepository.save(any())).thenReturn(user)
        val result = userService.generateUserLoginToken(userLogin)


        assertNotNull(result.token, "expected token to exist")
        assertNotNull(result.expiration, "expected expiration to exist")
    }

    @Test
    fun test_generateUserLoginToken_should_throw_InvalidUserCredentialsException() {
        val passwordEncoder = BCryptPasswordEncoder()
        val userLogin = UserLoginPayload("test@test.com", "abc")
        val user = User(
            name = "test",
            email = "test@test.com",
            password = passwordEncoder.encode("ddd")
        )

        `when`(userRepository.findByEmail(user.email)).thenReturn(user)

        assertThrows<InvalidUserCredentialsException> {
            userService.generateUserLoginToken(userLogin)
        }
    }

    @Test
    fun test_findByToken_should_return_correct_user() {
        val token = "test"
        val user = User(
            name = "test",
            email = "test@test.com",
            password = "abc",
            token = token,
            tokenExpires = Instant.now().plus(Duration.ofMinutes(10)).toEpochMilli()
        )

        `when`(userRepository.findByToken(token)).thenReturn(user)

        val result = userService.findByToken(token)

        assertNotNull(result, "expected user to exist")
        assertEquals(result, user)
    }

    @Test
    fun test_findByToken_should_return_null() {
        val token = "test"

        `when`(userRepository.findByToken(token)).thenReturn(null)

        val result = userService.findByToken(token)

        assertNull(result, "expected null result")
    }


}