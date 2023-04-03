package io.github.justinlewis.data.remote.rest

import io.github.justinlewis.data.DEVICE_TYPE_GCM
import io.github.justinlewis.data.remote.dto.ApiRespond
import io.github.justinlewis.data.remote.dto.MovieDTO
import io.github.justinlewis.data.remote.dto.PageListDTO
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created on 2/2/2018.
 */
interface ApiClient {

    @Suppress("unused")
    @FormUrlEncoded
    @POST("push/device")
    fun registerDeviceToken(
        @Header("Authorization") token: String,
        @Field("deviceToken") deviceToken: String,
        @Field("deviceType") deviceType: String = DEVICE_TYPE_GCM
    ): Single<ApiRespond<Any>>

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") token: String,
        @Query("page") page: Int
    ): Single<PageListDTO<MovieDTO>>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") token: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Single<PageListDTO<MovieDTO>>
}
