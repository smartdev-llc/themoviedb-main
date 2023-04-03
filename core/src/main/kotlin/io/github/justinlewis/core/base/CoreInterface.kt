package io.github.justinlewis.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created on 2/1/2018.
 */
interface CoreInterface {
    interface View
    interface ViewModel
    interface AndroidView : View {
        val layoutResId: Int
        /**
         * Created inflate view layout and onViewCreate reference to android view, attach android event handler
         * @param viewState save view state
         * @param inflater inflater to inflate view resource
         * @param container parent view
         */
        fun createView(
            viewState: Bundle?,
            layoutInflater: LayoutInflater,
            container: ViewGroup
        ): android.view.View {
            return layoutInflater.inflate(layoutResId, container, false)
        }

        /**
         * Setup android view reference
         */
        fun initView() {
            // do nothing here. For sub class implementation
        }

        /**
         * Setup view adapter, view listener
         */
        fun setupView() {
            // do nothing here. For sub class implementation
        }
        /**
         * Setup view observer to view model
         */
        fun bindViewModel() {
            // do nothing here. For sub class implementation
        }
    }

    interface AndroidViewModel : ViewModel {
        fun onViewCreated() {
            // do nothing here. For sub class implementation
        }
        fun onViewCreateUi() {
            // do nothing here. For sub class implementation
        }
        fun onViewStart() {
            // do nothing here. For sub class implementation
        }
        fun onViewPause() {
            // do nothing here. For sub class implementation
        }
        fun onViewResume() {
            // do nothing here. For sub class implementation
        }
        fun onViewStop() {
            // do nothing here. For sub class implementation
        }
        fun onViewDestroyUi() {
            // do nothing here. For sub class implementation
        }
        fun onViewDestroy() {
            // do nothing here. For sub class implementation
        }
        fun onBackPressed(): Boolean = false
    }
}
