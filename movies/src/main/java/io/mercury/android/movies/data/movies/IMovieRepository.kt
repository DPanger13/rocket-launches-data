package io.mercury.android.movies.data.movies

import io.reactivex.Flowable
import java.net.URI

interface IMovieRepository {

    data class TopMovie(
            val id: String,
            val rank: Int,
            val title: String,
            val year: Int,
            val poster: URI,
            val imdbInfo: ImdbInfo
    ) {
        data class ImdbInfo(val id: String, val rating: Double, val votes: Int, val imdbPage: URI?)
    }

    fun getTopMovies(): Flowable<List<TopMovie>>

}
