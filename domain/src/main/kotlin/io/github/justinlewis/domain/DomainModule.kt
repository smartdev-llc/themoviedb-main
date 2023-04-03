package io.github.justinlewis.domain

import dagger.Module
import dagger.Provides
import io.github.justinlewis.common.utils.DeviceUtil
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.data.remote.rest.ApiClient
import io.github.justinlewis.data.repo.LocalRepo
import io.github.justinlewis.domain.interactor.AuthenticationInteractor
import io.github.justinlewis.domain.interactor.MovieInteractor
import io.github.justinlewis.domain.interactor.UserInteractor
import io.github.justinlewis.domain.interactor.internal.AuthenticationInteractorImpl
import io.github.justinlewis.domain.interactor.internal.MovieInteractorImpl
import io.github.justinlewis.domain.interactor.internal.UserInteractorImpl

/**
 * Created on 2/7/2018.
 */
@Module()
class DomainModule {

    @Provides
    fun userInteractor(
        networkUtil: NetworkUtil,
        deviceUtil: DeviceUtil,
        resourceUtil: ResourceUtil,
        apiRepo: ApiClient,
        localRepo: LocalRepo
    ): UserInteractor = UserInteractorImpl(
        networkUtil,
        deviceUtil,
        resourceUtil,
        apiRepo,
        localRepo
    )

    @Provides
    fun movieInteractor(
        networkUtil: NetworkUtil,
        resourceUtil: ResourceUtil,
        apiRepo: ApiClient,
        localRepo: LocalRepo
    ): MovieInteractor = MovieInteractorImpl(
        networkUtil,
        resourceUtil,
        apiRepo,
        localRepo
    )

    @Provides
    fun authenInteractor(
        networkUtil: NetworkUtil,
        resourceUtil: ResourceUtil,
        apiRepo: ApiClient,
        localRepo: LocalRepo
    ): AuthenticationInteractor = AuthenticationInteractorImpl(
        networkUtil,
        resourceUtil,
        localRepo,
        apiRepo
    )
}
