package io.github.justinlewis.data.remote

import io.github.justinlewis.data.HTTP_APP_VERSION_HEADER
import io.github.justinlewis.data.HTTP_USER_AGENT_HEADER
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/**
 * Created on 4/9/2018.
 */
class ApiInterceptor(val userAgent: String, val appVersion: String) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
        newRequest.addHeader(HTTP_USER_AGENT_HEADER, userAgent)
        newRequest.addHeader(HTTP_APP_VERSION_HEADER, appVersion)
        return chain.proceed(newRequest.build())
    }
}
