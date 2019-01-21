package io.mercury.android.movies.data.movies

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.http.GET
import retrofit2.http.Query

internal interface LaunchApi {

    open class PagedLaunchSummaryDto(
            @SerializedName("launches") val launches: List<LaunchSummaryDto>,
            @SerializedName("offset") val offset: Int,
            @SerializedName("count") val count: Int,
            @SerializedName("total") val total: Int
    )

    data class LaunchSummaryDto(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,

            /**
             * Date and time of this launch formatted as Month dd, yyyy hh24:mi:ss UTC and can be
             * parsed with [dateTimeFormatter].
             */
            @SerializedName("net") val dateTime: String
    )

    /**
     * @param offset The launch to start at or null to start at the beginning.
     */
    @GET("launch")
    fun getUpcomingLaunches(@Query("offset") offset: Int? = null): Single<PagedLaunchSummaryDto>

    open class PagedLaunchDto(
            @SerializedName("launches") val launches: List<LaunchDto>,
            @SerializedName("offset") val offset: Int,
            @SerializedName("count") val count: Int,
            @SerializedName("total") val total: Int
    )

    data class LaunchDto(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,

            /**
             * Date and time of this launch formatted as Month dd, yyy hh24:mi:ss UTC and can be
             * parsed with [dateTimeFormatter].
             */
            @SerializedName("net") val dateTime: String,

            @SerializedName("location") val location: LocationDto,
            @SerializedName("rocket") val rocket: RocketDto,
            @SerializedName("missions") val missions: List<MissionDto>
    )

    data class LocationDto(@SerializedName("name") val name: String)

    data class RocketDto(@SerializedName("name") val name: String)

    data class MissionDto(@SerializedName("name") val name: String)

    /**
     * Represents the amount of information to return for an endpoint's objects.
     */
    enum class Mode constructor(val mode: String) {

        /**
         * Mode where only the most basic information for an endpoint's objects is returned.
         */
        SUMMARY("list"),

        /**
         * Mode where a reasonable amount of information for an endpoint's objects is returned from
         * an endpoint.
         */
        DEFAULT("summary"),

        /**
         * Mode where every piece of information in an endpoint's objects is returned from an
         * endpoint.
         */
        VERBOSE("verbose");

    }

    /**
     * @param mode One of the [Mode] types. The proper String can be retrieved with [Mode.mode].
     */
    @GET("launch")
    fun getLaunch(@Query("mode") mode: String, @Query("id") id: Int): Single<PagedLaunchDto>

    companion object {
        val dateTimeFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss 'UTC'")
    }

}
