package io.mercury.android.movies.data.movies

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Flowable
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.net.URI

interface IMovieRepository {

    fun getTopMovies(): Flowable<List<TopMovie>>

    fun getMovieInfo(imdbId: String): Flowable<DetailedMovieInfo>

}


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
) : MovieInfo(id, title, year, poster), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            URI(parcel.readString()),
            parcel.readInt(),
            ExtendedImdbInfo(parcel.readString()!!, parcel.readDouble(), parcel.readInt(), URI(parcel.readString()))
    )

    class ExtendedImdbInfo(
            id: String,
            rating: Double,
            votes: Int,
            val imdbPage: URI?
    ) : ImdbInfo(id, rating, votes)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(year)
        parcel.writeString(poster?.toString() ?: "")
        parcel.writeInt(rank)
        parcel.writeString(extendedImdbInfo.id)
        parcel.writeDouble(extendedImdbInfo.rating)
        parcel.writeInt(extendedImdbInfo.votes)
        parcel.writeString(extendedImdbInfo.imdbPage?.toString() ?: "")
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<TopMovie> {
            override fun createFromParcel(parcel: Parcel): TopMovie {
                return TopMovie(parcel)
            }

            override fun newArray(size: Int): Array<TopMovie?> {
                return arrayOfNulls(size)
            }
        }
    }
}

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
) : MovieInfo(id, title, year, poster), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            URI(parcel.readString()),
            ImdbInfo(parcel.readString()!!, parcel.readDouble(), parcel.readInt()),
            ContentRating.fromString(parcel.readString()!!),
            LocalDate.parse(parcel.readString(), DateTimeFormatter.BASIC_ISO_DATE),
            parcel.readInt(),
            parcel.createStringArrayList()!!,
            parcel.readString()!!,
            parcel.createStringArrayList()!!,
            parcel.createStringArrayList()!!,
            parcel.readString()!!,
            parcel.createStringArrayList()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt())

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(year)
        parcel.writeString(poster?.toString() ?: "")

        parcel.writeString(imdbInfo.id)
        parcel.writeDouble(imdbInfo.rating)
        parcel.writeInt(imdbInfo.votes)

        parcel.writeString(contentRating.name)
        parcel.writeString(releaseDate?.format(DateTimeFormatter.BASIC_ISO_DATE) ?: "")
        parcel.writeInt(runtimeInMinutes ?: 0)
        parcel.writeStringList(genres)
        parcel.writeString(director)
        parcel.writeStringList(writers)
        parcel.writeStringList(actors)
        parcel.writeString(plot)
        parcel.writeStringList(languages)
        parcel.writeString(country)
        parcel.writeString(awards)
        parcel.writeInt(metascore ?: 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR = object : Parcelable.Creator<DetailedMovieInfo> {
            override fun createFromParcel(parcel: Parcel): DetailedMovieInfo {
                return DetailedMovieInfo(parcel)
            }

            override fun newArray(size: Int): Array<DetailedMovieInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
