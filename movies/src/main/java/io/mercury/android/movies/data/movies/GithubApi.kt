package io.mercury.android.movies.data.movies

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET

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

}
