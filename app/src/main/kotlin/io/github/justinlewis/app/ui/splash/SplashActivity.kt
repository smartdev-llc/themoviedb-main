package io.github.justinlewis.app.ui.splash

import androidx.lifecycle.Observer
import io.github.justinlewis.app.AppActivityNavigator
import io.github.justinlewis.app.R
import io.github.justinlewis.core.view.CoreActivity

class SplashActivity : CoreActivity<SplashViewModel>() {

    private val activityNavigator by lazy { navigator as AppActivityNavigator }

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun setupView() {
        super.setupView()
        viewModel.showSplash.observe(
            this,
            Observer { show: Boolean? ->
                if (show == false) {
                    gotoHomeScreen()
                }
            }
        )
    }

    private fun gotoHomeScreen() {
        activityNavigator.gotoHomeScreen()
    }
}
