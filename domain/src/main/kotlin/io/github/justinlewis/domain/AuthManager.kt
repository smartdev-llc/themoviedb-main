package io.github.justinlewis.domain

import io.github.justinlewis.common.extension.dispatchAndSubscribe
import io.github.justinlewis.domain.interactor.AuthenticationInteractor
import io.github.justinlewis.domain.interactor.UserInteractor
import io.github.justinlewis.domain.pojo.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Suppress("unused")
@Singleton
class AuthManager @Inject constructor() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var userInteractor: UserInteractor

    @Inject
    lateinit var authenInteractor: AuthenticationInteractor

    private var user: User? = null

    fun hasUserInfo(): Boolean = synchronized(this) {
        user != null
    }

    fun getUser() = user!!

    fun loadUser(completeAction: ((User?) -> Unit)? = null) =
        userInteractor.loadUserInfo()
            .subscribe(
                { u ->
                    synchronized(this) {
                        this.user = u!!.copy() // sure that user info is not null
                        completeAction?.invoke(user)
                    }
                },
                { e ->
                    Timber.e(e)
                    completeAction?.invoke(null)
                }
            )

    fun clearAuthData(completeAction: ((Boolean) -> Unit)? = null): Disposable {
        user = null
        return authenInteractor.clearAuthData().dispatchAndSubscribe {
            doOnSuccess {
                completeAction?.invoke(true)
            }
            doOnError {
                completeAction?.invoke(false)
            }
        }
    }

    fun getToken(): String = user?.token ?: ""

    fun saveUserInfo(user: User) =
        userInteractor.saveUserInfo(user)
            .dispatchAndSubscribe {
                doShowError { Timber.w(it) }
                doOnSuccess { u ->
                    synchronized(this) { this@AuthManager.user = u.copy() }
                }
            }
}
