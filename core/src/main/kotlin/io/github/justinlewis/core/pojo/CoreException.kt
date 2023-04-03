package io.github.justinlewis.core.pojo

class CoreException(val code: Int = ERROR_CODE_UNKNOWN, message: String? = null, cause: Throwable? = null) :
    Throwable(message, cause) {
    companion object {
        const val ERROR_CODE_UNKNOWN = 1000
        const val ERROR_CODE_NETWORK = 2000
        const val ERROR_CODE_NETWORK_TIMEOUT = 2010
    }
}
