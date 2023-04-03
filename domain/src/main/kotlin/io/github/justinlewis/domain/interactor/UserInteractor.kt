package io.github.justinlewis.domain.interactor

import io.github.justinlewis.domain.pojo.User
import io.reactivex.Single

interface UserInteractor {
    fun loadUserInfo(): Single<User?>
    fun saveUserInfo(user: User): Single<User>

    fun registerDeviceToken(authorization: String, deviceToken: String): Single<Boolean>

    fun saveDeviceToken(token: String)
    fun getDeviceToken(): String
}
