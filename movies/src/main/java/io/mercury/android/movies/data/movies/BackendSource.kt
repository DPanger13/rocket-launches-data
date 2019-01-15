package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.MovieInfoDto
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.reactivex.Single

internal open class BackendSource(private val api: GithubApi) {

    open fun getTopMovies(): Single<List<TopMovieDto>> = api.getTopMovies()

    open fun getMovieInfo(id: String): Single<MovieInfoDto> = api.getMovieInfo(id)

}
