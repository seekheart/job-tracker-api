package dev.seekheart.jobtrackerapi.users.services

import dev.seekheart.jobtrackerapi.users.models.UserMapper
import dev.seekheart.jobtrackerapi.users.models.UserResponse
import dev.seekheart.jobtrackerapi.users.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, val userMapper: UserMapper) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun findAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { userMapper.userToUserResponse(it) }
    }

}