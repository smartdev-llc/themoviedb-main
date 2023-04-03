package io.github.justinlewis.domain.extension

import io.github.justinlewis.common.api.ApiConfig.FORCE_LOGOUT_CODE
import io.github.justinlewis.common.api.ApiConfig.FORCE_UPDATE_CODE
import io.github.justinlewis.common.api.ApiException
import io.github.justinlewis.data.remote.dto.ApiRespond
import io.github.justinlewis.domain.CODE_SUCCEED
import io.reactivex.Single

/**
 * An extension function maps each [ApiRespond.code] and the data of a [DTO] with a domain model or
 * an [ApiException].
 *
 * @receiver DTO the type of DTO class.
 * @receiver T the type of domain model class.
 *
 * @param transform maps DTO to a corresponding domain model.
 *
 * @return a [Single] of a domain model or an [ApiException].
 */
fun <DTO, T> Single<ApiRespond<DTO>>.handleApiRespondAndTransformData(transform: (DTO) -> T): Single<T> =
    map {
        val code = it.code
        val data = it.data
        when {
            code == FORCE_LOGOUT_CODE || code == FORCE_UPDATE_CODE -> throw ApiException(
                it.message ?: "", it.code
            )
            code == CODE_SUCCEED && data == null ->
                throw ApiException(it.message ?: "", code)
            code == CODE_SUCCEED && data != null -> transform(data)
            else -> throw ApiException(it.message ?: "", code)
        }
    }

fun <DTO> Single<ApiRespond<DTO>>.handleApiRespondAndTransformData(): Single<DTO> =
    map {
        val code = it.code
        val data = it.data
        when {
            code == FORCE_LOGOUT_CODE || code == FORCE_UPDATE_CODE -> throw ApiException(
                it.message ?: "", it.code
            )
            code == CODE_SUCCEED && data == null ->
                throw ApiException(it.message ?: "", code)
            code == CODE_SUCCEED && data != null -> data
            else -> throw ApiException(it.message ?: "", code)
        }
    }
