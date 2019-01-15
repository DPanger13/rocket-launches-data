package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.MovieInfoDto
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.reactivex.Single
import javax.inject.Inject

internal open class BackendSource @Inject constructor(private val api: GithubApi) {

    open fun getTopMovies(): Single<List<TopMovieDto>> = api.getTopMovies()

    open fun getMovieInfo(id: String): Single<MovieInfoDto> = api.getMovieInfo(id)

}
