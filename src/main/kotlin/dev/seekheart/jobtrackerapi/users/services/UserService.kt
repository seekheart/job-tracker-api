package dev.seekheart.jobtrackerapi.users.services

import com.fasterxml.jackson.databind.ObjectMapper
import dev.seekheart.jobtrackerapi.users.exceptions.UserAlreadyExistsException
import dev.seekheart.jobtrackerapi.users.exceptions.UserNotFoundException
import dev.seekheart.jobtrackerapi.users.models.UserPayload
import dev.seekheart.jobtrackerapi.users.models.toPayload
import dev.seekheart.jobtrackerapi.users.models.toUser
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository, val objectMapper: ObjectMapper) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

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

    fun save(userPayload: UserPayload): UserPayload {
        val record = userPayload.toUser()
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

}