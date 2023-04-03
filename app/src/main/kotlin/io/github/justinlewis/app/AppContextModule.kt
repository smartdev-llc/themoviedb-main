package io.github.justinlewis.app

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created on 4/10/2018.
 */

@Module
class AppContextModule(val context: Context) {

    /**
     * Provide application context binding (injection).
     * @return Context application context.
     */
    @Provides
    fun applicationContext() = context
}
