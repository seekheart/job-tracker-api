package dev.seekheart.jobtrackerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JobTrackerApiApplication

fun main(args: Array<String>) {
    runApplication<JobTrackerApiApplication>(*args)
}
