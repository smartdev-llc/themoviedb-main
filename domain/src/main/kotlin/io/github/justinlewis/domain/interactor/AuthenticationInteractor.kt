package io.github.justinlewis.domain.interactor

import io.reactivex.Single

interface AuthenticationInteractor {
    fun clearAuthData(): Single<Any>
}
