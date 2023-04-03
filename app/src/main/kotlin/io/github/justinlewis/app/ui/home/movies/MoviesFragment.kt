package io.github.justinlewis.app.ui.home.movies

import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.paulrybitskyi.persistentsearchview.PersistentSearchView
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import io.github.justinlewis.app.R
import io.github.justinlewis.app.ui.base.fragment.BaseApiFragment
import io.github.justinlewis.common.utils.ItemClickSupport
import io.github.justinlewis.common.view.EndlessRecyclerViewScrollListener

/**
 * Created on 5/16/2019.
 */
class MoviesFragment : BaseApiFragment<MoviesViewModel>() {
    override val layoutResId: Int
        get() = R.layout.fragment_movies

    @BindView(R.id.rv_datas)
    lateinit var vData: RecyclerView

    @BindView(R.id.tv_empty)
    lateinit var vEmptyText: TextView

    @BindView(R.id.sv_search)
    lateinit var vSearchView: PersistentSearchView

    private val adapter = MoviesAdapter()

    override fun setupView() {
        super.setupView()
        setHasOptionsMenu(true)
        setupMovieList()
        setupSearchView()
    }

    private fun setupSearchView() {
        with(vSearchView) {
            setOnLeftBtnClickListener {
                vSearchView.inputQuery = ""
                viewModel.exitSearch()
                vSearchView.collapse()
            }
            setOnClearInputBtnClickListener {
                viewModel.resetSearch()
            }

            // Setting a delegate for the voice recognition input
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@MoviesFragment))

            setOnSearchConfirmedListener { searchView, query ->
                viewModel.searchMovies(query)
                searchView.collapse()
            }

            // Disabling the suggestions since they are unused in
            // the simple implementation
            setSuggestionsDisabled(true)
        }
    }

    private fun setupMovieList() {
        vData.adapter = adapter
        ItemClickSupport.addTo(vData).setOnItemClickListener { _, _, _ ->
        }
        vData.addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(vData.layoutManager!!) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.onLoadMore()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_film_list, menu)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        viewModel.displayMovies.observe(
            viewLifecycleOwner
        ) {
            vEmptyText.isVisible = it.dataList.isEmpty()
            vData.isVisible = it.dataList.isNotEmpty()
            adapter.submitList(it.dataList)
        }
    }

    override fun onDestroyView() {
        vData.adapter = null
        super.onDestroyView()
    }
}
