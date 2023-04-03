package io.github.justinlewis.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.github.justinlewis.app.ui.error.ErrorViewModel
import io.github.justinlewis.app.ui.home.HomeViewModel
import io.github.justinlewis.app.ui.home.movies.MoviesViewModel
import io.github.justinlewis.app.ui.splash.SplashViewModel
import io.github.justinlewis.core.di.ViewModelKey
import io.github.justinlewis.core.viewmodel.AppViewModelFactory
import javax.inject.Singleton

/**
 * Created on 3/21/2018.
 */

@Module
abstract class AppViewModelModule {
    /**
     * Bind [App View Model Factory][io.github.justinlewis.core.viewmodel.AppViewModelFactory] for create and provide view model.
     */
    @Binds
    @Singleton
    abstract fun viewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    /**
     * Bind [Home View Model][io.github.justinlewis.app.ui.home.HomeViewModel] for [Home activity][io.github.justinlewis.app.ui.home.HomeActivity].
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    /**
     * Bind [Splash View Model][io.github.justinlewis.app.ui.splash.SplashViewModel] for [Splash activity][io.github.justinlewis.app.ui.splash.SplashActivity].
     */
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    /**
     * Bind [Movies List View Model][io.github.justinlewis.app.ui.home.movies.MoviesViewModel] for [movie list tab][io.github.justinlewis.app.ui.home.movies.MoviesFragment].
     */
    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun mvViewModel(viewModel: MoviesViewModel): ViewModel

    /**
     * Bind [Movies Detail View Model][io.github.justinlewis.app.ui.home.movies.detail.ErrorViewModel] for [error screen][io.github.justinlewis.app.ui.error.ErrorActivity].
     */
    @Binds
    @IntoMap
    @ViewModelKey(ErrorViewModel::class)
    abstract fun errorViewModel(viewModel: ErrorViewModel): ViewModel
}
