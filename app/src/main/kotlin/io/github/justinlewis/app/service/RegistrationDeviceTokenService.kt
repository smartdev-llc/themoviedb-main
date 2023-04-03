package io.github.justinlewis.app.service

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.github.justinlewis.app.KEY_DEVICE_TOKEN
import io.github.justinlewis.app.util.ContextInjection
import io.github.justinlewis.common.extension.dispatchAndSubscribe
import io.github.justinlewis.domain.AuthManager
import io.github.justinlewis.domain.interactor.UserInteractor
import javax.inject.Inject
import timber.log.Timber

/**
 * Created on 4/9/2018.
 */
class RegistrationDeviceTokenService(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @Inject
    lateinit var userInteractor: UserInteractor

    @Inject
    lateinit var authManager: AuthManager

    init {
        ContextInjection.inject(to = this, with = context)
    }

    override fun doWork(): Result {
        Log.i(TAG, "onStartJob")
        var token = inputData.getString(KEY_DEVICE_TOKEN)
        Log.i(TAG, "onStartJob $token")
        if (token.isNullOrEmpty()) {
            token = userInteractor.getDeviceToken()
        }
        val context = applicationContext
        userInteractor.saveDeviceToken(token)
        authManager.loadUser()
        if (authManager.hasUserInfo()) {
            userInteractor.registerDeviceToken(authManager.getToken(), token)
                .dispatchAndSubscribe {
                    doOnSuccess { isSucceeded ->
                        Timber.i("registerDeviceToken isSucceeded: $isSucceeded")
                        if (!isSucceeded) {
                            scheduleSendDeviceToken(context, token)
                        }
                    }
                    doShowError {
                        Timber.i("RegistrationDeviceTokenService registerDeviceToken failed")
                        scheduleSendDeviceToken(context, token)
                    }
                }
        } else {
            scheduleSendDeviceToken(context, token)
        }
        return Result.success()
    }

    companion object {
        private const val TAG = "RegistrationDeviceToken"
        fun scheduleSendDeviceToken(context: Context, token: String) {
            Timber.tag(TAG).i("token $token")
            val data = Data.Builder()
            data.putString(KEY_DEVICE_TOKEN, token)
            val refreshConstraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val refreshWorkManager = WorkManager.getInstance(context)
            val refreshTokenRequest =
                OneTimeWorkRequest.Builder(RegistrationDeviceTokenService::class.java)
                    .setConstraints(refreshConstraint)
                    .setInputData(data.build())
                    .addTag(TAG)
                    .build()
            refreshWorkManager.enqueue(refreshTokenRequest)
        }
    }
}
