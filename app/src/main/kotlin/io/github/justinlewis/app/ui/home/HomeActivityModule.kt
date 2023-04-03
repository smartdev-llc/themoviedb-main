package io.github.justinlewis.app.ui.home

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.justinlewis.app.AppActivityNavigator
import io.github.justinlewis.app.ui.home.movies.MoviesFragment
import io.github.justinlewis.app.ui.home.movies.MoviesModule
import io.github.justinlewis.core.ActivityNavigator
import io.github.justinlewis.core.di.PerActivity
import io.github.justinlewis.core.di.PerFragment
import io.github.justinlewis.core.view.CoreActivity

/**
 * Created  on 11/20/2018.
 */
@Module
abstract class HomeActivityModule {
    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: HomeActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator

    @PerFragment
    @ContributesAndroidInjector(modules = [MoviesModule::class])
    abstract fun movieFragment(): MoviesFragment
}
