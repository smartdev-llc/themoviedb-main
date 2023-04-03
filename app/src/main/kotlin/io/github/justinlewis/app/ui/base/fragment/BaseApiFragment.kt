package io.github.justinlewis.app.ui.base.fragment

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import butterknife.BindView
import io.github.justinlewis.app.R
import io.github.justinlewis.app.ui.base.api.ApiViewContract
import io.github.justinlewis.app.ui.base.viewmodel.BaseApiViewModel
import io.github.justinlewis.core.view.CoreFragment

/**
 * Created on 3/15/2018.
 */
abstract class BaseApiFragment<P : BaseApiViewModel> : CoreFragment<P>(), ApiViewContract {
    @BindView(R.id.fl_loading)
    lateinit var vLoading: View

    override fun bindViewModel() {
        super<CoreFragment>.bindViewModel()
        viewModel.loading.observe(
            viewLifecycleOwner,
            {
                vLoading.isVisible = it
            }
        )
        viewModel.error.observe(
            viewLifecycleOwner,
            {
                showError(it.message!!)
            }
        )
    }

    override fun showLoading() {
        vLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vLoading.visibility = View.GONE
    }

    override fun showError(error: String) {
        // For ex
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}
