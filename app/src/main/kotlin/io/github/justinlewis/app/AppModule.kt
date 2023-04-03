package io.github.justinlewis.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.github.justinlewis.app.service.RegisterDeviceTokenServiceModule
import io.github.justinlewis.app.service.RegistrationDeviceTokenService
import io.github.justinlewis.app.ui.error.ErrorActivity
import io.github.justinlewis.app.ui.error.ErrorActivityModule
import io.github.justinlewis.app.ui.home.HomeActivity
import io.github.justinlewis.app.ui.home.HomeActivityModule
import io.github.justinlewis.app.ui.splash.SplashActivity
import io.github.justinlewis.app.ui.splash.SplashActivityModule
import io.github.justinlewis.core.di.PerActivity

/**
 * Created on 3/21/2018.
 */

@Module(includes = [AndroidSupportInjectionModule::class, AppViewModelModule::class])
abstract class AppModule {
    /**
     * Add [Splash Activity module][io.github.justinlewis.app.ui.splash.SplashActivityModule] module to app dependency graph.
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivityInjector(): SplashActivity

    /**
     * Add [Error Activity module][io.github.justinlewis.app.ui.splash.SplashActivityModule] module to app dependency graph.
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [ErrorActivityModule::class])
    abstract fun errorActivityInjector(): ErrorActivity

    /**
     * Add [Registration Device Token Service module][io.github.justinlewis.app.service.RegisterDeviceTokenServiceModule] to app dependency graph for register push notification token.
     */
    @ContributesAndroidInjector(modules = [RegisterDeviceTokenServiceModule::class])
    abstract fun registrationDeviceTokenService(): RegistrationDeviceTokenService

    /**
     * Add [Home Activity module][io.github.justinlewis.app.ui.home.HomeActivityModule] to app dependency graph for register push notification token.
     */
    @PerActivity
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivityInjector(): HomeActivity
}
