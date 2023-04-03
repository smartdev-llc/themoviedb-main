package io.github.justinlewis.core.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.justinlewis.core.view.CoreFragment

/**
 * Created on 2/13/2018.
 */
abstract class BaseFragmentPagerAdapter<T : CoreFragment<*>> constructor(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val registeredFragments: SparseArray<T> = SparseArray<T>()
    var currentPosition: Int = 0

    // Register the fragment when the item is instantiated
    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(container: ViewGroup, position: Int): T {
        val fragment = super.instantiateItem(container, position) as T
        registeredFragments.put(position, fragment)
        onInstantiateItem(position)
        return fragment
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, item: Any) {
        super.setPrimaryItem(container, position, item)
        currentPosition = position
    }

    @SuppressWarnings("unused")
    protected fun onInstantiateItem(position: Int) {
    }

    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
        onDestroyItem(position)
    }

    @SuppressWarnings("unused")
    protected fun onDestroyItem(position: Int) {
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): T = registeredFragments.get(position)

    fun getActiveFragment(): T = registeredFragments.get(currentPosition)
}
