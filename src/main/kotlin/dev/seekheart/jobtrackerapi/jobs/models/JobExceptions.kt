package dev.seekheart.jobtrackerapi.jobs.models

import org.springframework.data.crossstore.ChangeSetPersister
import java.util.*

class JobNotFoundException(id: UUID) : ChangeSetPersister.NotFoundException() {
    override val message: String = "Job with id $id not found"
}