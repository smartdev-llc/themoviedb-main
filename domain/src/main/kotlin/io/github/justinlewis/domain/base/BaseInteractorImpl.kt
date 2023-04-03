package io.github.justinlewis.domain.base

import io.github.justinlewis.common.api.ApiException
import io.github.justinlewis.common.api.ERROR_CODE_NETWORK
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.data.remote.dto.ApiRespond
import io.github.justinlewis.domain.R
import io.github.justinlewis.domain.extension.handleApiRespondAndTransformData
import io.reactivex.Single

open class BaseInteractorImpl(
    private val networkUtil: NetworkUtil,
    private val resourceUtil: ResourceUtil
) {

    internal fun <DTO, T> Single<ApiRespond<DTO>>.handleNetworkCallAndTransformData(transform: (DTO) -> T): Single<T> =
        if (networkUtil.isConnected()) {
            handleApiRespondAndTransformData(transform)
        } else {
            val exception = ApiException(
                errorMessage = resourceUtil.getStringResource(R.string.network_error),
                code = ERROR_CODE_NETWORK
            )
            Single.error(exception)
        }

    internal fun <DTO> Single<ApiRespond<DTO>>.handleNetworkCallAndGetData(): Single<DTO> =
        if (networkUtil.isConnected()) {
            handleApiRespondAndTransformData()
        } else {
            val exception = ApiException(
                errorMessage = resourceUtil.getStringResource(R.string.network_error),
                code = ERROR_CODE_NETWORK
            )
            Single.error(exception)
        }
}
