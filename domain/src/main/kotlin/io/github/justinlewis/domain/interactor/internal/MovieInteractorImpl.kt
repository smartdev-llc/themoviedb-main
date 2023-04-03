package io.github.justinlewis.domain.interactor.internal

import io.github.justinlewis.common.utils.NetworkUtil
import io.github.justinlewis.common.utils.ResourceUtil
import io.github.justinlewis.data.remote.rest.ApiClient
import io.github.justinlewis.data.repo.LocalRepo
import io.github.justinlewis.domain.interactor.MovieInteractor
import io.github.justinlewis.domain.pojo.Movie
import io.github.justinlewis.domain.pojo.PagingData
import io.github.justinlewis.domain.transform.toMovie
import io.github.justinlewis.domain.transform.toPageList
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created  on 11/20/2018.
 */
internal class MovieInteractorImpl @Inject constructor(
    networkUtil: NetworkUtil,
    resourceUtil: ResourceUtil,
    private val apiRepo: ApiClient,
    private val localRepo: LocalRepo
) : BaseInteractor(networkUtil, resourceUtil), MovieInteractor {

    override fun getPopularMovies(page: Int): Single<PagingData<Movie>> =
        checkNetWorkAndExecuteRequest {
            localRepo.loadUserInfo().flatMap {
                apiRepo.getPopularMovie(it.token, page)
            }.map {
                it.toPageList { movieDto ->
                    movieDto.toMovie()
                }
            }
        }

    override fun searchMovies(query: String, page: Int): Single<PagingData<Movie>> =
        checkNetWorkAndExecuteRequest {
            localRepo.loadUserInfo().flatMap {
                apiRepo.searchMovies(it.token, query, page)
            }.map {
                it.toPageList { movieDto ->
                    movieDto.toMovie()
                }
            }
        }
}
