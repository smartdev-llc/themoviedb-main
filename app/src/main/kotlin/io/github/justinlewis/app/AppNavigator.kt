package io.github.justinlewis.app

import android.content.Intent
import io.github.justinlewis.app.ui.home.HomeActivity
import io.github.justinlewis.core.ActivityNavigator
import io.github.justinlewis.core.FragmentNavigator
import io.github.justinlewis.core.view.CoreActivity
import io.github.justinlewis.core.view.CoreFragment
import javax.inject.Inject

/**
 * Created on 2/2/2018.
 */
class AppActivityNavigator @Inject constructor(activity: CoreActivity<*>) :
    ActivityNavigator(activity) {

    fun gotoHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}

class AppFragmentNavigator @Inject constructor(fragment: CoreFragment<*>) : FragmentNavigator(
    fragment
)
