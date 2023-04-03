package io.github.justinlewis.core.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import io.github.justinlewis.core.base.CoreInterface.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created on 5/17/2019.
 */
open class CoreViewModel @Inject constructor() : ViewModel(), AndroidViewModel {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onReceivedArgument(arguments: Bundle) {
        // For child class implementation
    }
}
