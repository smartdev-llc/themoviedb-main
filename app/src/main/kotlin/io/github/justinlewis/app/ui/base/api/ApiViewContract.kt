package io.github.justinlewis.app.ui.base.api

import io.github.justinlewis.core.base.CoreInterface

/**
 * Created on 3/15/2018.
 */
interface ApiViewContract : CoreInterface.AndroidView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
}
