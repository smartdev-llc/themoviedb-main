package io.github.justinlewis.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PageListDTO<T>(
    @SerializedName("page")
    val page: Int = 0, // 1
    @SerializedName("results")
    val results: List<T> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0, // 993
    @SerializedName("total_results")
    val totalResults: Int = 0 // 19844
)
