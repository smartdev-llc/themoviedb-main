package io.github.justinlewis.domain.pojo

data class PagingData<T>(
    val page: Int = 0, // 1
    val dataList: List<T> = listOf(),
    val totalPages: Int = 0, // 993
    val totalResults: Int = 0 // 19844
)
