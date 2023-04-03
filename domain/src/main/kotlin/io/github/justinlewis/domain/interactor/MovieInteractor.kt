package io.github.justinlewis.domain.interactor

import io.github.justinlewis.domain.pojo.Movie
import io.github.justinlewis.domain.pojo.PagingData
import io.reactivex.Single

/**
 * Created  on 11/20/2018.
 */
interface MovieInteractor {
    fun getPopularMovies(page: Int): Single<PagingData<Movie>>
    fun searchMovies(query: String, page: Int): Single<PagingData<Movie>>
}
