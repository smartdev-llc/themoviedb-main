package io.github.justinlewis.app.ui.base.activity

import android.view.View
import android.widget.Toast
import butterknife.BindView
import io.github.justinlewis.app.R
import io.github.justinlewis.app.ui.base.api.ApiViewContract
import io.github.justinlewis.app.ui.base.viewmodel.BaseApiViewModel
import io.github.justinlewis.core.view.CoreActivity

/**
 * Created on 3/15/2018.
 */
abstract class BaseApiActivity<P : BaseApiViewModel> : CoreActivity<P>(), ApiViewContract {
    @BindView(R.id.fl_loading)
    lateinit var vLoading: View

    override fun showLoading() {
        vLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vLoading.visibility = View.GONE
    }

    override fun showError(error: String) {
        // For ex
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}
