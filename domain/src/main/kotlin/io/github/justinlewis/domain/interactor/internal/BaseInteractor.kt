package io.github.justinlewis.domain.interactor.internal

import io.github.justinlewis.common.api.ApiException
import io.github.justinlewis.common.api.ERROR_CODE_NETWORK
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.domain.R
import io.reactivex.Single

open class BaseInteractor(val networkUtil: NetworkUtil, val resourceUtil: ResourceUtil) {

    protected fun <T> checkNetWorkAndExecuteRequest(singleCall: () -> Single<T>): Single<T> {
        return if (networkUtil.isConnected()) {
            singleCall.invoke()
        } else {
            Single.fromCallable {
                throw ApiException(
                    resourceUtil.getStringResource(R.string.network_error),
                    ERROR_CODE_NETWORK
                )
            }
        }
    }
}
