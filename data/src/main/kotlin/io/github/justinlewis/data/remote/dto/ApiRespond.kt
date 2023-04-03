package io.github.justinlewis.data.remote.dto

import com.google.gson.annotations.SerializedName
import io.github.justinlewis.common.api.SimpleApiRespond

/**
 * Created on 3/8/2018.
 */
class ApiRespond<out T : Any?>(
    code: Int,
    message: String,
    @SerializedName("data")
    val data: T,
    @SerializedName("version")
    var versionInfo: VersionInfo
) : SimpleApiRespond(code, message)
