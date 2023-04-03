package io.github.justinlewis.app

import dagger.Component
import dagger.android.AndroidInjector
import io.github.justinlewis.common.UtilComponent
import io.github.justinlewis.domain.DomainComponent
import javax.inject.Singleton

/**
 * Created on 3/21/2018.
 */

@Singleton
@Component(
    modules = [AppModule::class, AppContextModule::class],
    dependencies = [DomainComponent::class, UtilComponent::class]
)
interface AppComponent : AndroidInjector<App> {
    override fun inject(app: App)
}
