package io.github.justinlewis.domain.transform

import io.github.justinlewis.data.remote.dto.PageListDTO
import io.github.justinlewis.domain.pojo.PagingData

/**
 * Created on 5/16/2019.
 */
fun <T, R> PageListDTO<T>.toPageList(transFun: (T) -> R) = PagingData<R>(
    page,
    results.map { transFun(it) },
    totalPages,
    totalResults
)
