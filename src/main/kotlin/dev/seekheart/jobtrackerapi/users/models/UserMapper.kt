package dev.seekheart.jobtrackerapi.users.models

import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun userToUserResponse(user: User): UserResponse
    fun userResponseToUser(userResponse: UserResponse): User
}