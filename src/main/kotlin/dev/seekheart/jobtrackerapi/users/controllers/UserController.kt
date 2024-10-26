package dev.seekheart.jobtrackerapi.users.controllers

import dev.seekheart.jobtrackerapi.users.models.UserResponse
import dev.seekheart.jobtrackerapi.users.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun findAllUsers(): List<UserResponse> {
        return userService.findAllUsers()
    }

}