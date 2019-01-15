package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.MovieInfoDto
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.mercury.android.movies.data.movies.IMovieRepository.DetailedMovieInfo
import io.mercury.android.movies.data.movies.IMovieRepository.DetailedMovieInfo.ContentRating
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie.ExtendedImdbInfo
import io.reactivex.Flowable
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
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

    override fun getMovieInfo(imdbId: String): Flowable<DetailedMovieInfo> =
            backendSource.getMovieInfo(imdbId)
                    .toFlowable()
                    .map { it.toDetailedMovieInfo() }
}

internal fun TopMovieDto.toTopMovie(): TopMovie {
    var pageUri: URI? = null
    try {
        pageUri = URI(imdbPageLink)
    } catch (exception: URISyntaxException) {
        val msg = "Couldn't parse imdb page URI. Using null. URI: $imdbPageLink"
        Timber.e(exception, msg)
    }

    val imdbInfo = ExtendedImdbInfo(imdbId, imdbRating, numVotes, pageUri)

    var posterUri: URI? = null
    try {
        posterUri = URI(posterLink)
    } catch (exception: URISyntaxException) {
        val msg = "Couldn't parse poster URI. Using null, URI: $posterLink"
        Timber.e(exception, msg)
    }

    return TopMovie(imdbId, title, year, posterUri, rank, imdbInfo)
}

internal fun MovieInfoDto.toDetailedMovieInfo(): DetailedMovieInfo {
    var posterUri: URI? = null
    try {
        posterUri = URI(posterLink)
    } catch (exception: URISyntaxException) {
        val msg = "Parsing poster URI failed. Using null. URI: $posterLink"
        Timber.e(exception, msg)
    }

    val imdbInfo = IMovieRepository.ImdbInfo(imdbId, imdbRating, numImdbVotes)

    val datePattern = "d LLL yyyy"
    var parsedReleaseDate: LocalDate? = null
    try {
        parsedReleaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern(datePattern))
    } catch (exception: DateTimeParseException) {
        val msg = "Parsing DTO date failed. Using null. DTO date: $releaseDate. Pattern: $datePattern"
        Timber.e(exception, msg)
    }

    val runtimeMinuteFormat = " min"
    var runtimeInMinutes: Int? = null
    try {
        runtimeInMinutes = runtime.replaceFirst(runtimeMinuteFormat, "").toInt()
    } catch (exception: NumberFormatException) {
        val msg = "Parsing DTO runtime failed. Using null. DTO runtime: $runtime. Removed: $runtimeMinuteFormat"
        Timber.e(exception, msg)
    }

    var metascoreAsInt: Int? = null
    try {
        metascoreAsInt = metascore.toInt()
    } catch (exception: NumberFormatException) {
        val msg = "Converting metascore to int failed. Using null. Metascore: $metascore"
        Timber.e(exception, msg)
    }

    val splitWriters =
            if (writers.isEmpty()) listOf()
            else writers.split(", ")

    return DetailedMovieInfo(
            imdbId,
            title,
            year,
            posterUri,
            imdbInfo,
            ContentRating.fromString(contentRating),
            parsedReleaseDate,
            runtimeInMinutes,
            genres,
            director,
            splitWriters,
            actors,
            plot,
            languages,
            country,
            awards,
            metascoreAsInt
    )
}
