package io.github.justinlewis.data.repo

import android.content.Context
import io.github.justinlewis.common.utils.PreferenceUtil
import io.github.justinlewis.data.local.KEY_DEVICE_TOKEN
import io.github.justinlewis.data.local.entity.UserEntity
import io.github.justinlewis.data.local.helper.UserHelper
import io.reactivex.Single

/**
 * Created on 2/2/2018.
 */
internal class DbRepo(context: Context) : LocalRepo, PreferenceUtil(context) {
    private val prefs by lazy { defaultPref() }

    //region token
    override fun saveDeviceToken(token: String) {
        prefs[KEY_DEVICE_TOKEN] = token
    }

    override fun getDeviceToken(): String = prefs[KEY_DEVICE_TOKEN] ?: ""
    //endregion

    //region user
    override fun loadUserInfo(): Single<UserEntity> {
        return UserHelper().loadUserInfo()
    }

    override fun saveUserInfo(user: UserEntity): Single<UserEntity> =
        UserHelper().saveUserInfo(user)
    //endregion

    override fun clearAuthData() {
        // TODO to real implement
    }
}
