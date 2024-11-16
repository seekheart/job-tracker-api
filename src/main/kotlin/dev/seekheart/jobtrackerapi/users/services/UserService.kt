package dev.seekheart.jobtrackerapi.users.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.users.models.*
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository, val objectMapper: ObjectMapper) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()

    private fun saltPassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun findAll(): List<UserPayload> {
        return userRepository.findAll().map { it.toPayload() }
    }

    fun findById(id: UUID): UserPayload {
        val user = userRepository.findById(id).map { it.toPayload() }.getOrNull()
        if (user == null) {
            logger.error("User with id $id not found")
            throw UserNotFoundException(id)
        }
        return user
    }

    fun save(userPayload: UserRegisterPayload): UserPayload {
        val record = userPayload.apply {
            password = saltPassword(password)
        }.toUser()
        if (userRepository.findByName(userPayload.name) != null) {
            logger.error("User = ${record.name} already exists.")
            throw UserAlreadyExistsException(record.name)
        }

        userRepository.save(record)
        return record.toPayload()
    }

    fun update(id: UUID, userPayload: UserPayload): UserPayload {
        var record = userRepository.findById(id).getOrNull()
        if (record != null) {
            userPayload.id = record.id
            record = objectMapper.updateValue(record, userPayload)
            userRepository.save(record)
            return record.toPayload()
        } else {
            logger.error("User = $id not found")
            throw UserNotFoundException(id)
        }
    }

    fun delete(id: UUID) {
        if (userRepository.findById(id).isPresent) {
            userRepository.deleteById(id)
        } else {
            logger.error("User = ${id} not found")
            throw UserNotFoundException(id)
        }
    }

    fun validateUserLogin(userLoginPayload: UserLoginPayload): Boolean {
        val email = userLoginPayload.email
        val userEnteredPassword = userLoginPayload.password
        val user = userRepository.findByEmail(email)

        if (user == null) {
            logger.error("User with email = $email not found")
            throw UserEmailNotFoundException(email)
        }

        return passwordEncoder.matches(userEnteredPassword, user.password)
    }

    fun isValidToken(token: String): Boolean {
        val tokenExists = userRepository.existsByToken(token)

        if (!tokenExists) {
            return false
        }

        val expiration = userRepository.findByToken(token)?.tokenExpires
        return if (expiration != null) {
            Instant.now().isBefore(Instant.ofEpochMilli(expiration))
        } else {
            false
        }
    }

    fun generateUserLoginToken(userLoginPayload: UserLoginPayload): UserTokenPayload {
        if (validateUserLogin(userLoginPayload)) {
            val token = UUID.randomUUID().toString()
            val expiration = Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
            val user = userRepository.findByEmail(userLoginPayload.email)!!.apply {
                this.token = token
                this.tokenExpires = expiration
            }
            logger.info("Generating token and updating user info")
            userRepository.save(user)
            return UserTokenPayload(token = token, expiration = expiration)
        } else {
            throw InvalidUserCredentialsException()
        }
    }

    fun findByToken(token: String): User? {
        return userRepository.findByToken(token)
    }

}