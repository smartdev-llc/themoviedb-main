package io.github.justinlewis.data

import dagger.Component
import io.github.justinlewis.data.local.DbComponent
import io.github.justinlewis.data.remote.NetComponent
import io.github.justinlewis.data.remote.rest.ApiClient
import io.github.justinlewis.data.repo.LocalRepo

/**
 * Created on 2/9/2018.
 */
// @Singleton
@Component(dependencies = [NetComponent::class, DbComponent::class])
interface DataComponent {
    fun exposeLocalRepo(): LocalRepo

    fun exposeApiRepo(): ApiClient
}
