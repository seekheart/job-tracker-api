package dev.seekheart.jobtrackerapi.users.services

import dev.seekheart.jobtrackerapi.users.errors.UserAlreadyExistsError
import dev.seekheart.jobtrackerapi.users.models.UserMapper
import dev.seekheart.jobtrackerapi.users.models.UserPayload
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, val userMapper: UserMapper) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAll(): List<UserPayload> {
        return userRepository.findAll().map { userMapper.userToUserPayload(it) }
    }

    fun save(userPayload: UserPayload): UserPayload {
        val record = userMapper.userPayloadToUser(userPayload)
        if (userRepository.findByName(record.name) != null) {
            logger.error("User = ${record.name} already exists.")
            throw UserAlreadyExistsError(record.name)
        }
        userRepository.save(record)
        return userMapper.userToUserPayload(record)
    }

}