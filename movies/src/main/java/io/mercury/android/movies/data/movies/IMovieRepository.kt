package io.mercury.android.movies.data.movies

import io.reactivex.Flowable
import org.threeten.bp.LocalDate
import java.net.URI

interface IMovieRepository {

    open class MovieInfo(
            val id: String,
            val title: String,
            val year: Int,
            val poster: URI?
    )

    open class ImdbInfo(val id: String, val rating: Double, val votes: Int)

    class TopMovie(
            id: String,
            title: String,
            year: Int,
            poster: URI?,
            val rank: Int,
            val extendedImdbInfo: ExtendedImdbInfo
    ) : MovieInfo(id, title, year, poster) {
        class ExtendedImdbInfo(
                id: String,
                rating: Double,
                votes: Int,
                val imdbPage: URI?
        ) : ImdbInfo(id, rating, votes)
    }

    fun getTopMovies(): Flowable<List<TopMovie>>

    class DetailedMovieInfo(
            id: String,
            title: String,
            year: Int,
            poster: URI?,
            val imdbInfo: ImdbInfo,
            val contentRating: ContentRating,

            /**
             * The movie's release date or null if it couldn't be provided.
             */
            val releaseDate: LocalDate?,

            /**
             * The movie's runtime, in minutes, or null if it couldn't be provided.
             */
            val runtimeInMinutes: Int?,
            val genres: List<String>,
            val director: String,
            val writers: List<String>,
            val actors: List<String>,
            val plot: String,
            val languages: List<String>,
            val country: String,
            val awards: String,

            /**
             * This movie's Metascore, a rating from 0 - 100, or null if it couldn't be provided.
             */
            val metascore: Int?
    ) : MovieInfo(id, title, year, poster) {

        enum class ContentRating {

            /**
             * A content rating of restricted, meaning only people that fulfill certain requirements
             * may watch the movie.
             */
            R,

            UNKNOWN;

            companion object {

                fun fromString(string: String) =
                        when (string) {
                            "R" -> R
                            else -> UNKNOWN
                        }

            }

        }

    }

    fun getMovieInfo(imdbId: String): Flowable<DetailedMovieInfo>

}
