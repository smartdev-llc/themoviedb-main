package io.github.justinlewis.app.ui.error

import android.os.Bundle
import android.widget.Button
import io.github.justinlewis.app.R
import io.github.justinlewis.core.view.CoreActivity
import org.acra.dialog.CrashReportDialogHelper

class ErrorActivity : CoreActivity<ErrorViewModel>() {
    override val layoutResId: Int
        get() = R.layout.activity_error

    private val helper by lazy {
        CrashReportDialogHelper(this, intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.bt_restart).setOnClickListener {
            helper.sendCrash(null, null)
            finish()
        }
    }

    override fun setupView() {
        super.setupView()
    }
}
