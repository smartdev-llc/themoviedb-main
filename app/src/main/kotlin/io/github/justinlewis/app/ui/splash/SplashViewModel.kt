package io.github.justinlewis.app.ui.splash

import androidx.lifecycle.MutableLiveData
import io.github.justinlewis.common.extension.dispatchAndSubscribe
import io.github.justinlewis.core.viewmodel.CoreViewModel
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */
class SplashViewModel @Inject constructor() : CoreViewModel() {

    val showSplash = MutableLiveData<Boolean>()

    override fun onViewCreated() {
        super.onViewCreated()
        showSplash.postValue(true)
    }

    override fun onViewStart() {
        super.onViewStart()
        Single.timer(SPLASH_TIME, TimeUnit.MILLISECONDS).dispatchAndSubscribe {
            doOnSuccess { showSplash.postValue(false) }
        }
    }

    companion object {
        const val SPLASH_TIME = 2000L
    }
}
