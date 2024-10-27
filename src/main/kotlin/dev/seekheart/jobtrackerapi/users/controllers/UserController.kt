package dev.seekheart.jobtrackerapi.users.controllers

import dev.seekheart.jobtrackerapi.users.models.UserPayload
import dev.seekheart.jobtrackerapi.users.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun findAllUsers(): List<UserPayload> {
        return userService.findAll()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): UserPayload {
        return userService.findById(id)
    }

    @PostMapping
    fun saveUser(@RequestBody userPayload: UserPayload): ResponseEntity<UserPayload> {
        val payload = userService.save(userPayload)
        return ResponseEntity(payload, HttpStatus.CREATED)
    }
}