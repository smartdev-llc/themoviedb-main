package io.github.justinlewis.domain.interactor.internal

import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.data.remote.rest.ApiClient
import io.github.justinlewis.data.repo.LocalRepo
import io.github.justinlewis.domain.base.BaseInteractorImpl
import io.github.justinlewis.domain.interactor.AuthenticationInteractor
import io.reactivex.Single
import javax.inject.Inject

class AuthenticationInteractorImpl @Inject constructor(
    networkUtil: NetworkUtil,
    resourceUtil: ResourceUtil,
    private val localRepo: LocalRepo,
    private val apiRepo: ApiClient
) : BaseInteractorImpl(networkUtil, resourceUtil), AuthenticationInteractor {
    // region Authentication

    override fun clearAuthData(): Single<Any> =
        Single.fromCallable { localRepo.clearAuthData() }
    // endregion
}
