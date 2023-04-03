package io.github.justinlewis.domain.transform

import io.github.justinlewis.data.local.entity.UserEntity
import io.github.justinlewis.domain.pojo.User

/**
 * Created on 5/16/2019.
 */
fun User.toEntity() = UserEntity(userId, token)
fun UserEntity.toUser() = User(userId, token)
