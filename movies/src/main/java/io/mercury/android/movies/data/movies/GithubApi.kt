package io.mercury.android.movies.data.movies

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GithubApi {

    data class TopMovieDto(
            @SerializedName("rank") val rank: Int,
            @SerializedName("title") val title: String,
            @SerializedName("year") val year: Int,
            @SerializedName("imdbId") val imdbId: String,
            @SerializedName("imdbRating") val imdbRating: Double,
            @SerializedName("imdbVotes") val numVotes: Int,

            /**
             * Link, as a URL, to the poster.
             */
            @SerializedName("poster") val posterLink: String,

            /**
             * Link, as a URL, to the IMDB page.
             */
            @SerializedName("imdbLink") val imdbPageLink: String
    )

    @GET("top_movies.json")
    fun getTopMovies(): Single<List<TopMovieDto>>

    data class MovieInfoDto(
            @SerializedName("imdbRating") val imdbRating: Double,
            @SerializedName("imdbVotes") val numImdbVotes: Int,
            @SerializedName("imdbId") val imdbId: String,
            @SerializedName("title") val title: String,
            @SerializedName("year") val year: Int,

            /**
             * This movie's content rating (ie, PG-13).
             */
            @SerializedName("rated") val contentRating: String,

            /**
             * This movie's release date, in DD-MMM-YYYY format (ie, 24 Mar 1972).
             */
            @SerializedName("released") val releaseDate: String,

            /**
             * This movie's runtime, as a user-readable String (ie, 175 minutes).
             */
            @SerializedName("runtime") val runtime: String,

            @SerializedName("genre") val genres: List<String>,
            @SerializedName("director") val director: String,

            /**
             * This movie's list of writers, as a user-readable String.
             */
            @SerializedName("writer") val writers: String,
            @SerializedName("actors") val actors: List<String>,
            @SerializedName("plot") val plot: String,
            @SerializedName("language") val languages: List<String>,
            @SerializedName("country") val country: String,

            /**
             * This movie's awards, as a user-readable String.
             */
            @SerializedName("awards") val awards: String,

            /**
             * Link to this movie's poster, as a URL.
             */
            @SerializedName("poster") val posterLink: String,

            /**
             * This movie's Metascore, as a number from 0 to 100.
             */
            @SerializedName("metascore") val metascore: String
    )

    @GET("by_id/{imdbId}.json")
    fun getMovieInfo(@Path("imdbId") imdbId: String): Single<MovieInfoDto>

}
