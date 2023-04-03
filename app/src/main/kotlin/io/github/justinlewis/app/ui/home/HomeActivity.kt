package io.github.justinlewis.app.ui.home

import androidx.navigation.ui.setupActionBarWithNavController
import io.github.justinlewis.app.R
import io.github.justinlewis.core.view.CoreActivity

/**
 * Created  on 11/20/2018.
 */
class HomeActivity : CoreActivity<HomeViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_home

    override val navHostId: Int
        get() = R.id.home_nav_host

    override fun setupView() {
        super.setupView()
        val navController = findNavigationController()!!
        setupActionBarWithNavController(navController)
    }
}
