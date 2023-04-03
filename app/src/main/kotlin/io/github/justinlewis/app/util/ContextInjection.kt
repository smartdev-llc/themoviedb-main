package io.github.justinlewis.app.util

import android.content.Context
import dagger.android.HasAndroidInjector

object ContextInjection {
    @JvmStatic
    fun inject(to: Any, with: Context) {
        (with.applicationContext as HasAndroidInjector).androidInjector().inject(to)
    }
}
