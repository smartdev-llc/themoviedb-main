package io.github.justinlewis.app.ui.base.viewmodel

import androidx.lifecycle.LiveData
import com.hadilq.liveevent.LiveEvent
import io.github.justinlewis.core.pojo.CoreException
import io.github.justinlewis.core.viewmodel.CoreViewModel

/**
 * Created on 5/20/2019.
 */
open class BaseApiViewModel : CoreViewModel() {

    protected val lLoading = LiveEvent<Boolean>()
    val loading: LiveData<Boolean>
        get() = lLoading
    protected val lError = LiveEvent<CoreException>()
    val error: LiveData<CoreException>
        get() = lError
}
