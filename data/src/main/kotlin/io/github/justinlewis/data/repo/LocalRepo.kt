package io.github.justinlewis.data.repo

import io.github.justinlewis.data.local.entity.UserEntity
import io.reactivex.Single

/**
 * Created on 2/2/2018.
 */
interface LocalRepo {
    /**
     * save user info from preference
     */
    fun saveDeviceToken(token: String)

    /**
     * load user info from preference
     */
    fun getDeviceToken(): String

    /**
     * load user info from db
     */
    fun loadUserInfo(): Single<UserEntity>

    /**
     * save user info from db
     */
    fun saveUserInfo(user: UserEntity): Single<UserEntity>

    /**
     * Clear authenticate data
     */
    fun clearAuthData()
}
