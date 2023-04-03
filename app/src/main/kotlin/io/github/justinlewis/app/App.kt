package io.github.justinlewis.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.justinlewis.app.ui.error.ErrorActivity
import io.github.justinlewis.common.DaggerUtilComponent
import io.github.justinlewis.common.UtilModule
import io.github.justinlewis.common.api.ApiConfig
import io.github.justinlewis.data.DaggerDataComponent
import io.github.justinlewis.data.fakeUser
import io.github.justinlewis.data.local.DaggerDbComponent
import io.github.justinlewis.data.local.DbModule
import io.github.justinlewis.data.remote.DaggerNetComponent
import io.github.justinlewis.data.remote.NetModule
import io.github.justinlewis.domain.DaggerDomainComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import org.acra.ACRA
import org.acra.config.dialog
import org.acra.config.httpSender
import org.acra.config.scheduler
import org.acra.data.StringFormat
import org.acra.ktx.initAcra
import org.acra.sender.HttpSender
import timber.log.Timber

/**
 * Created on 9/27/2017.
 */
class App @Inject constructor() : DaggerApplication() {
    /**
     * @property kAppComponent app component for injection
     */
    val kAppComponent: AppComponent by lazy {
        val utilComponent = DaggerUtilComponent.builder().utilModule(UtilModule(this)).build()
        val netComponent = DaggerNetComponent.builder().netModule(
            NetModule(
                this,
                BuildConfig.SERVER_API_URI
            )
        ).build()
        val dbComponent = DaggerDbComponent.builder().dbModule(DbModule(this)).utilComponent(
            utilComponent
        ).build()
        val dataComponent = DaggerDataComponent.builder().netComponent(netComponent).dbComponent(
            dbComponent
        ).build()
        val domainComponent = DaggerDomainComponent.builder()
            .dataComponent(dataComponent)
            .utilComponent(utilComponent)
            .build()
        DaggerAppComponent.builder()
            .appContextModule(AppContextModule(this))
            .utilComponent(utilComponent).domainComponent(domainComponent)
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = kAppComponent

    override fun onCreate() {
        super.onCreate()
        fakeUser.token = BuildConfig.SERVER_API_KEY // TODO remove fake user logic code
        kAppComponent.inject(this)
        configRealm()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        configApiHandler()
        configCrashReportHandler()
    }

    private fun configCrashReportHandler() {
        initAcra {
            // core configuration:
            enabled = true
            buildConfigClass = BuildConfig::class.java
            if (BuildConfig.DEBUG) {
                ACRA.DEV_LOGGING = true
            }

            // Show activity when crash happened
            dialog {
                text = "Opps"
                withReportDialogClass(ErrorActivity::class.java)
            }

//            //Uncomment bellow code for send additional application log content
//            val fullReportContent =
//                arrayOf<ReportField>(*reportContent, ReportField.APPLICATION_LOG)
//            reportContent = fullReportContent
//            val file = File(filesDir, "applog.txt")
//            applicationLogFile = file.absolutePath

            // dump logcat content for crash log
            withLogcatArguments("-d")
            reportSendFailureToast = getString(R.string.send_report_failed_message)
            reportSendSuccessToast = getString(R.string.send_report_succeed_message)

            // Send crash log to server
            reportFormat = StringFormat.JSON
            httpSender {
                enabled = true
                uri = BuildConfig.SERVER_LOG_URI // should end with '/report'
                basicAuthLogin = BuildConfig.SERVER_LOG_ACC
                basicAuthPassword = BuildConfig.SERVER_LOG_PASS
                httpMethod = HttpSender.Method.POST
            }

            // Send crash with scheduler
            scheduler {
                withEnabled(true)
            }
        }
    }

    private fun configRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun configApiHandler() {
        ApiConfig.IS_DEBUG = BuildConfig.SHOW_LOG
        ApiConfig.defaultErrorMessage = getString(R.string.all_general_error)
        ApiConfig.onForceLogoutHandler = { error: Throwable ->
            synchronized(this@App) {
                // TODO this way prevent Home screen reopen. It's better if change to use schedule service or pending intent but, user have to wait more. Other solution to use single top but may be its not function if open new task (for ex: open from notification)
                if (!ApiConfig.isForceLogout) {
                    ApiConfig.isForceLogout = true
                    // Do something to logout here. For ex: clear data and go to login activity
                }
            }
        }
        ApiConfig.onForceUpdateHandler = { error: Throwable ->
            synchronized(this@App) {
                // TODO this way prevent Home screen reopen. It's better if change to use schedule service or pending intent but, user have to wait more. Other solution to use single top but may be its not function if open new task (for ex: open from notification)
                if (!ApiConfig.isNewAppVersionAvailable) {
                    ApiConfig.isNewAppVersionAvailable = true
                    // Do something to force update here. For ex: reopen app and recheck version
                }
            }
        }
    }
}
