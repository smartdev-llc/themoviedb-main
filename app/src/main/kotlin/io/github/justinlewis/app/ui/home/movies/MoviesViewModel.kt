package io.github.justinlewis.app.ui.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.justinlewis.app.ui.base.viewmodel.BaseApiViewModel
import io.github.justinlewis.common.extension.addTo
import io.github.justinlewis.common.extension.dispatchAndSubscribe
import io.github.justinlewis.core.pojo.CoreException
import io.github.justinlewis.domain.interactor.MovieInteractor
import io.github.justinlewis.domain.pojo.Movie
import io.github.justinlewis.domain.pojo.PagingData
import javax.inject.Inject

/**
 * Created on 5/16/2019.
 */
class MoviesViewModel @Inject constructor(private val movieInteractor: MovieInteractor) :
    BaseApiViewModel() {

    private var searchPage: Int = 0
    private var searchQuery: String = ""

    private var _popularMovies = PagingData<Movie>()
    private val _displayMovies = MutableLiveData<PagingData<Movie>>()
    val displayMovies: LiveData<PagingData<Movie>>
        get() = _displayMovies

    init {
        loadMoviesByPage(1)
    }

    private fun loadMoviesByPage(page: Int) {
        lLoading.value = true
        movieInteractor.getPopularMovies(page).dispatchAndSubscribe {
            doHideLoading {
                lLoading.value = false
            }
            doOnSuccess {
                updateMovies(it)
                _popularMovies =
                    _displayMovies.value?.copy(
                        dataList = mutableListOf<Movie>().apply {
                            addAll(
                                displayMovies.value!!.dataList
                            )
                        }
                    )
                        ?: PagingData()
            }
            doShowError {
                lError.value = CoreException(message = it)
            }
        }.addTo(compositeDisposable)
    }

    private fun updateMovies(mvs: PagingData<Movie>) {
        if (mvs.page == 1) {
            _displayMovies.value = mvs
        } else {
            val oldMovies = _displayMovies.value!!.dataList
            val newMovies = mvs.dataList
            val gotMovies = _displayMovies.value!!.copy(
                page = mvs.page,
                totalPages = mvs.totalPages,
                dataList = oldMovies + newMovies
            )
            _displayMovies.value = gotMovies
        }
    }

    fun onLoadMore() {
        if (searchQuery.isNotBlank()) {
            searchMovies(searchQuery, isNewSearch = false)
        } else if (_displayMovies.value!!.page < _displayMovies.value!!.totalPages) {
            loadMoviesByPage(_displayMovies.value!!.page + 1)
        }
    }

    fun searchMovies(query: String?, isNewSearch: Boolean = true) {
        if (query.isNullOrBlank()) return
        // handle if is new search or load more
        if (isNewSearch) {
            val trimQuery = query.trim()
            if (trimQuery == searchQuery) return
            searchQuery = trimQuery
            searchPage = 1
        } else {
            searchPage++
        }
        // start search
        lLoading.value = true
        movieInteractor.searchMovies(searchQuery, searchPage).dispatchAndSubscribe {
            doHideLoading {
                lLoading.value = false
            }
            doOnSuccess {
                updateMovies(it)
            }
            doShowError {
                lError.value = CoreException(message = it)
            }
        }.addTo(compositeDisposable)
    }

    /**
     * Call when user exit search mode
     */
    fun exitSearch() {
        _displayMovies.value = _popularMovies
        resetSearch()
    }

    /**
     * Call when user clear search query
     */
    fun resetSearch() {
        searchQuery = ""
        searchPage = 1
    }
}
