package io.github.justinlewis.common.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created  on 3/14/2018.
 */
class ResourceUtil(val context: Context) {
    fun getStringResource(@StringRes stringRes: Int) = context.getString(stringRes)
}
