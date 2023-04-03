package io.github.justinlewis.common

import dagger.Component
import io.github.justinlewis.common.utils.DeviceUtil
import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil

/**
 * Created on 2/13/2018.
 */
@Component(modules = [(UtilModule::class)])
interface UtilComponent {
    fun networkUtil(): NetworkUtil
    fun resourceUtil(): ResourceUtil
    fun deviceUtil(): DeviceUtil
}
