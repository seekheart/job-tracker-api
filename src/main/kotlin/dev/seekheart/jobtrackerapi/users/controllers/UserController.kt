package dev.seekheart.jobtrackerapi.users.controllers

import dev.seekheart.jobtrackerapi.trackers.models.TrackerPayload
import dev.seekheart.jobtrackerapi.trackers.services.TrackerService
import dev.seekheart.jobtrackerapi.users.models.UserLoginPayload
import dev.seekheart.jobtrackerapi.users.models.UserPayload
import dev.seekheart.jobtrackerapi.users.models.UserRegisterPayload
import dev.seekheart.jobtrackerapi.users.models.UserTokenPayload
import dev.seekheart.jobtrackerapi.users.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService, val trackerService: TrackerService) {
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
    fun saveUser(@RequestBody userPayload: UserRegisterPayload): ResponseEntity<UserPayload> {
        val payload = userService.save(userPayload)
        return ResponseEntity(payload, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody userPayload: UserPayload): ResponseEntity<Unit> {
        userService.update(id, userPayload)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Unit> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/auth")
    fun validateUserLogin(@RequestBody userLoginPayload: UserLoginPayload): UserTokenPayload {
        logger.debug("Validating user login")
        return userService.generateUserLoginToken(userLoginPayload)
    }

    @PostMapping("/{id}/trackers")
    fun createTrackerForUser(
        @PathVariable id: UUID,
        @RequestBody tracker: TrackerPayload
    ): ResponseEntity<TrackerPayload> {
        val response = trackerService.saveTrackerToUser(id, tracker)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{id}/trackers")
    fun getTrackersForUser(@PathVariable id: UUID): List<TrackerPayload> {
        return trackerService.getTrackersByUserId(id)
    }

}