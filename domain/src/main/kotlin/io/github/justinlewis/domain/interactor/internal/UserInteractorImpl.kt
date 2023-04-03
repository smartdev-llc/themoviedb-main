package io.github.justinlewis.domain.interactor.internal

import io.github.justinlewis.common.utils.DeviceUtil
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.data.remote.rest.ApiClient
import io.github.justinlewis.data.repo.LocalRepo
import io.github.justinlewis.domain.interactor.UserInteractor
import io.github.justinlewis.domain.pojo.User
import io.github.justinlewis.domain.transform.toEntity
import io.github.justinlewis.domain.transform.toUser
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created on 3/21/2018.
 */
@Suppress("unused")
internal class UserInteractorImpl @Inject constructor(
    private val networkUtil: NetworkUtil,
    private val deviceUtil: DeviceUtil,
    val resourceUtil: ResourceUtil,
    private val apiRepo: ApiClient,
    private val localRepo: LocalRepo
) : UserInteractor {

    override fun loadUserInfo(): Single<User?> {
        return localRepo.loadUserInfo().map { it.toUser() }
    }

    override fun saveUserInfo(user: User): Single<User> =
        localRepo.saveUserInfo(user.toEntity()).map { it.toUser() }

    //region device token
    override fun registerDeviceToken(
        authorization: String,
        deviceToken: String
    ): Single<Boolean> {
        // TODO Not use for demo, uncomment it if develop real app
//        return apiRepo.registerDeviceToken(
//            "$KEY_BEARER $authorization",
//            deviceToken
//        ).handleNetworkCallAndTransformData {
//            it
//        }
        return Single.just(false)
    }

    override fun saveDeviceToken(token: String) {
        localRepo.saveDeviceToken(token)
    }

    override fun getDeviceToken() = localRepo.getDeviceToken()
}
