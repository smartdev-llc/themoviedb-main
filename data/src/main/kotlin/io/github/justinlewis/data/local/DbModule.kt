package io.github.justinlewis.data.local

import android.app.Application
import dagger.Module
import dagger.Provides
import io.github.justinlewis.data.repo.DbRepo
import io.github.justinlewis.data.repo.LocalRepo

/**
 * Created on 2/2/2018.
 */
@Module
class DbModule(val application: Application) {
    @Provides
    fun provideLocalRepo(): LocalRepo = DbRepo(application)
}
