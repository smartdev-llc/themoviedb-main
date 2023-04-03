package io.github.justinlewis.app.ui.splash

import dagger.Binds
import dagger.Module
import io.github.justinlewis.app.AppActivityNavigator
import io.github.justinlewis.core.ActivityNavigator
import io.github.justinlewis.core.di.PerActivity
import io.github.justinlewis.core.view.CoreActivity

/**
 * Created on 2/1/2018.
 */
@Module
abstract class SplashActivityModule {
    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: SplashActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator
}
