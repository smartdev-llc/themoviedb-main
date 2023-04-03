package io.github.justinlewis.core.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerAppCompatActivity
import io.github.justinlewis.core.ActivityNavigator
import io.github.justinlewis.core.adapter.BaseFragmentPagerAdapter
import io.github.justinlewis.core.base.CoreInterface.AndroidView
import io.github.justinlewis.core.viewmodel.CoreViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */
abstract class CoreActivity<P : CoreViewModel> :
    DaggerAppCompatActivity(),
    AndroidView {
    open val navHostId = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: P

    @Inject
    lateinit var navigator: ActivityNavigator

    private lateinit var viewUnbinder: Unbinder
    protected val compositeDisposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = createView(
            savedInstanceState,
            layoutInflater,
            findViewById(android.R.id.content)
        )
        setContentView(view)
        viewUnbinder = ButterKnife.bind(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get((this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<P>)
        intent.extras?.run { viewModel.onReceivedArgument(this) }
        viewModel.onViewCreated()
        initView()
        setupView()
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

    override fun onDestroy() {
        compositeDisposable.clear()
        viewModel.onViewDestroyUi()
        viewUnbinder.unbind()
        viewModel.onViewDestroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        return handleBackAction()
    }

    override fun onBackPressed() {
        if (supportActionBar == null) {
            handleBackAction()
        } else {
            super.onBackPressed()
        }
    }

    open fun handleBackAction(): Boolean {
        return if (navHostId > 0) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(navHostId) as NavHostFragment
            val currentFragment = navHostFragment.childFragmentManager.fragments[0]
            if (currentFragment is CoreFragment<*> && currentFragment.onBackPressed()) {
                true
            } else {
                val navController = navHostFragment.navController
                navController.navigateUp() || super.onSupportNavigateUp()
            }
        } else {
            super.onSupportNavigateUp()
        }
    }

    fun findNavigationController(): NavController? =
        if (navHostId > 0) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(navHostId) as NavHostFragment
            navHostFragment.navController
        } else {
            null
        }

    abstract class CoreTabActivity<P : CoreViewModel> : CoreActivity<P>() {
        open lateinit var pagerAdapter: BaseFragmentPagerAdapter<*>

        override fun onBackPressed() {
            val activeFragment = pagerAdapter.getActiveFragment()
            if (!activeFragment.onBackPressed()) {
                finish()
                return
            }
        }
    }
}
