package io.github.justinlewis.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import io.github.justinlewis.core.view.CoreActivity

/**
 * Created on 2/1/2018.
 */

/**
 * This class is for control fragment/activity navigation implementation in an activity
 */
abstract class Navigator(val activity: AppCompatActivity) {
    open val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    /**
     * Back to root fragment
     */

    fun backToRoot(): Boolean {
        if (fragmentManager.backStackEntryCount <= 1)
            return false
        val rootFragmentName = fragmentManager.getBackStackEntryAt(0).name
        fragmentManager.popBackStackImmediate(rootFragmentName, 0)
        return true
    }

    abstract fun findNavigationController(): NavController?
}

/**
 * This class is for control fragment navigation implementation in an activity or activity in application
 */
abstract class ActivityNavigator(activity: CoreActivity<*>) : Navigator(activity) {

    override fun findNavigationController() =
        (activity as CoreActivity<*>).findNavigationController()
}

/**
 * This class is for control fragment navigation implementation in an activity
 */
abstract class FragmentNavigator(val fragment: Fragment) :
    Navigator(fragment.activity as AppCompatActivity) {
    override val fragmentManager: FragmentManager
        get() = fragment.childFragmentManager

    override fun findNavigationController() = fragment.findNavController()
}
