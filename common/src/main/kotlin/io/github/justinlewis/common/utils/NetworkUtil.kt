package io.github.justinlewis.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil(private val context: Context) {

    fun get(): Context = context

    @SuppressLint("MissingPermission")
    fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null &&
            connectivityManager.activeNetworkInfo?.isAvailable == true &&
            connectivityManager.activeNetworkInfo?.isConnected == true
    }
}
