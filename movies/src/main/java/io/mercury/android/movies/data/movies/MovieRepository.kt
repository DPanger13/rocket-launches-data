package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie.ImdbInfo
import io.reactivex.Flowable
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException

internal class MovieRepository(private val backendSource: BackendSource) : IMovieRepository {

    override fun getTopMovies(): Flowable<List<TopMovie>> =
            backendSource.getTopMovies()
                    .toFlowable()
                    .map {
                        val topMovies = mutableListOf<TopMovie>()
                        for (dto in it) {
                            topMovies.add(dto.toTopMovie())
                        }

                        topMovies
                    }

}

internal fun TopMovieDto.toTopMovie(): TopMovie {
    var pageUri: URI? = null
    try {
        pageUri = URI(imdbPageLink)
    } catch (exception: URISyntaxException) {
        val msg = "Couldn't parse imdb page URI. Using null. URI: $imdbPageLink"
        Timber.e(exception, msg)
    }

    val imdbInfo = ImdbInfo(imdbId, imdbRating, numVotes, pageUri)

    return TopMovie(imdbId, rank, title, year, URI(posterLink), imdbInfo)
}
