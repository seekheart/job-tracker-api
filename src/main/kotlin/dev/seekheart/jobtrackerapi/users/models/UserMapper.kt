package dev.seekheart.jobtrackerapi.users.models

import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun userToUserPayload(user: User): UserPayload
    fun userPayloadToUser(userPayload: UserPayload): User
}