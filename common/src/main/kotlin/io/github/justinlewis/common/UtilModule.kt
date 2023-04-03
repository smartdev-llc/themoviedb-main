package io.github.justinlewis.common

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.justinlewis.common.utils.DeviceUtil
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil

/**
 * Created on 3/21/2018.
 */

@Module
class UtilModule(val context: Context) {
    @Provides
    fun provideNetworkUtil(): NetworkUtil = NetworkUtil(context)

    @Provides
    fun provideResourceUtil(): ResourceUtil = ResourceUtil(context)

    @Provides
    fun provideDeviceUtil(): DeviceUtil = DeviceUtil(context)
}
