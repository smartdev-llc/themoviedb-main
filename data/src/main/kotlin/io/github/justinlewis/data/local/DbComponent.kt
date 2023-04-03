package io.github.justinlewis.data.local

import dagger.Component
import io.github.justinlewis.common.UtilComponent
import io.github.justinlewis.data.repo.LocalRepo

/**
 * Created on 2/9/2018.
 */
// @Singleton
@Component(modules = [DbModule::class], dependencies = [UtilComponent::class])
interface DbComponent {
    fun exposeLocalRepo(): LocalRepo
}
