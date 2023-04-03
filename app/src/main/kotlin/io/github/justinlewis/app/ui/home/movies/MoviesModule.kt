package io.github.justinlewis.app.ui.home.movies

import dagger.Binds
import dagger.Module
import io.github.justinlewis.app.AppFragmentNavigator
import io.github.justinlewis.core.FragmentNavigator
import io.github.justinlewis.core.di.PerFragment
import io.github.justinlewis.core.view.CoreFragment

/**
 * Created on 5/16/2019.
 */
@Module
abstract class MoviesModule {
    @Binds
    @PerFragment
    abstract fun fragment(fragment: MoviesFragment): CoreFragment<*>

    @Binds
    @PerFragment
    abstract fun navigator(navigator: AppFragmentNavigator): FragmentNavigator
}
