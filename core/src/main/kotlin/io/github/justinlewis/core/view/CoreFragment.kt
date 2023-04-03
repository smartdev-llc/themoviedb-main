package io.github.justinlewis.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Transition
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerFragment
import io.github.justinlewis.core.FragmentNavigator
import io.github.justinlewis.core.base.CoreInterface.AndroidView
import io.github.justinlewis.core.viewmodel.CoreViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */

abstract class CoreFragment<P : CoreViewModel> : DaggerFragment(), AndroidView {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: P

    @Inject
    lateinit var navigator: FragmentNavigator

    open val fragmentContainerId: Int = 0
    private lateinit var viewUnbinder: Unbinder

    protected val compositeDisposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelObject: P by createViewModelLazy(
            ((this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<P>).kotlin, { this.viewModelStore },
            {
                viewModelFactory
            }
        )
        viewModel = viewModelObject
        arguments?.run { viewModel.onReceivedArgument(this) }
        onCreateFragment(savedInstanceState)
        viewModel.onViewCreated()
    }

    open fun onCreateFragment(savedInstanceState: Bundle?) {
        // do nothing here. For sub class implementation
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = createView(savedInstanceState, inflater, container!!)
        viewUnbinder = ButterKnife.bind(this, view)
        initView()
        setupView()
        bindViewModel()
        viewModel.onViewCreateUi()
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.onViewStart()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResume()
    }

    override fun onPause() {
        viewModel.onViewPause()
        super.onPause()
    }

    override fun onStop() {
        viewModel.onViewStop()
        super.onStop()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        viewModel.onViewDestroyUi()
        viewUnbinder.unbind()
        super.onDestroyView()
    }

    override fun onDestroy() {
        viewModel.onViewDestroy()
        super.onDestroy()
    }

    open fun onBackPressed(): Boolean = viewModel.onBackPressed()

    companion object {
        /**
         * Make new fragment instance with additional argument
         *
         * @param fragmentClass class instance
         * @param argument for new fragment instance
         * @param sharedElementEnterTransition shared element enter transition
         * @param enterTransition enter transition
         * @param exitTransition exit transition
         * @return new fragment instance
         */
        @JvmStatic
        fun newInstance(
            fragmentClass: Class<out CoreFragment<*>>,
            argument: Bundle? = null,
            sharedElementEnterTransition: Transition? = null,
            enterTransition: Transition? = null,
            exitTransition: Transition? = null
        ): CoreFragment<*> {
            val newFragment: CoreFragment<*> = fragmentClass.newInstance()
            try {
                newFragment.arguments = argument
                newFragment.sharedElementEnterTransition = sharedElementEnterTransition
                newFragment.enterTransition = enterTransition
                newFragment.exitTransition = exitTransition
            } catch (pE: Exception) {
                //                Timber.e(pE)
            }
            return newFragment
        }
    }
}
