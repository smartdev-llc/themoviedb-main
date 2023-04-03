package io.github.justinlewis.data.remote

import dagger.Component
import io.github.justinlewis.data.remote.rest.ApiClient

/**
 * Created on 3/7/2018.
 */
// @Singleton
@Component(modules = [NetModule::class])
interface NetComponent {
    fun apiRepo(): ApiClient
}
