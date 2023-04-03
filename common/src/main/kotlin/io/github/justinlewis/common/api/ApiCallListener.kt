package io.github.justinlewis.common.api

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.reactivex.exceptions.CompositeException
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created on 3/20/2018.
 */

class ApiCallListener<T> : DisposableSingleObserver<T>() {
    private var onSuccessHandler: ((T) -> Unit)? = null
    private var onErrorHandler: ((Throwable) -> Unit)? = null
    private val onForceLogoutHandler: ((Throwable) -> Unit)? = ApiConfig.onForceLogoutHandler
    private val onForceUpdateHandler: ((Throwable) -> Unit)? = ApiConfig.onForceUpdateHandler
    private var onShowErrorHandler: ((String) -> Unit)? = null
    private var onHideLoadingHandler: (() -> Unit)? = null
    private var onStartHandler: (() -> Unit)? = null

    fun doOnSuccess(handle: ((T) -> Unit)?) {
        onSuccessHandler = handle
    }

    fun doHideLoading(handle: (() -> Unit)?) {
        onHideLoadingHandler = handle
    }

    fun doShowError(handle: ((String) -> Unit)?) {
        onShowErrorHandler = handle
    }

    @Suppress("unused")
    fun doOnError(handle: ((Throwable) -> Unit)?) {
        onErrorHandler = handle
    }

    fun doOnStart(handle: (() -> Unit)?) {
        onStartHandler = handle
    }

    override fun onError(error: Throwable) {
        invokeHideLoading()
        Timber.e(error)
        when (error) {
            is HttpException -> {
                handleHttpCallError(error)
            }
            is CompositeException -> {
                invokeShowError(ApiConfig.defaultErrorMessage)
            }
            is ApiException -> {
                // handle fore update and force logout
                handleApiException(error)
            }
            else -> {
                // handle other exception
                val msg = if (ApiConfig.IS_DEBUG) error.message else ApiConfig.defaultErrorMessage
                if (!msg.isNullOrEmpty()) {
                    invokeShowError(msg)
                }
            }
        }
    }

    private fun handleApiException(error: ApiException) {
        when (error.code) {
            ApiConfig.FORCE_LOGOUT_CODE -> onForceLogoutHandler?.invoke(error)
            ApiConfig.FORCE_UPDATE_CODE -> onForceUpdateHandler?.invoke(error)

            // handle additional error on end point
            else -> {
                invokeOnError(error)
                val msg = if (!ApiConfig.IS_DEBUG && error.code == ERROR_CODE_GENERAL) ApiConfig.defaultErrorMessage else error.message
                if (!msg.isNullOrEmpty()) {
                    invokeShowError(error.message ?: "")
                }
            }
        }
    }

    private fun handleHttpCallError(error: HttpException) {
        var body: ResponseBody? = null
        try {
            body = error.response()!!.errorBody()
        } catch (ex: NullPointerException) {
            Timber.log(0, ex)
        }
        val errorRespondString = if (body != null) String(body.bytes()) else ""
        val errorBodyObj: SimpleApiRespond? = getParsedError(errorRespondString)
        Timber.e(error)
        if (errorBodyObj != null) {
            onShowErrorHandler?.let { handler ->
                if (!errorBodyObj.message.isNullOrBlank()) {
                    handler.invoke(errorBodyObj.message)
                }
            }
        } else {
            if (ApiConfig.IS_DEBUG) {
                invokeShowError(error.toString())
            } else {
                invokeShowError(ApiConfig.defaultErrorMessage)
            }
        }
    }

    private fun getParsedError(errorJson: String): SimpleApiRespond? {
        var errorBodyObj: SimpleApiRespond? = null
        try {
            errorBodyObj = Gson().fromJson<SimpleApiRespond>(
                errorJson,
                SimpleApiRespond::class.java
            )
        } catch (ex: NullPointerException) {
            Timber.log(0, ex)
        } catch (ex: JsonParseException) {
            Timber.log(0, ex)
        } catch (ex: JsonSyntaxException) {
            Timber.log(0, ex)
        }
        return errorBodyObj
    }

    override fun onSuccess(t: T) {
        invokeHideLoading()
        try {
            onSuccessHandler?.invoke(t)
        } catch (ex: Exception) {
            try {
                invokeShowError(ApiConfig.defaultErrorMessage)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        invokeOnStart()
    }

    private fun invokeOnStart() {
        try {
            onStartHandler?.invoke()
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun invokeOnError(error: Throwable) {
        try {
            onErrorHandler?.invoke(error)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun invokeShowError(message: String) {
        try {
            onShowErrorHandler?.invoke(message)
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }

    private fun invokeHideLoading() {
        try {
            onHideLoadingHandler?.invoke()
        } catch (ex: Exception) {
            try {
                onShowErrorHandler?.invoke(ApiConfig.defaultErrorMessage)
            } catch (ex: Exception) {
                Timber.e(ex)
            }
        }
    }
}
