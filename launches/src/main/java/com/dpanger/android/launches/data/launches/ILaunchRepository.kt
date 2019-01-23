package com.dpanger.android.launches.data.launches

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Flowable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

interface ILaunchRepository {

    /**
     * @param offset The offset to start at when retrieving launches. If null, launches are
     * retrieved from the beginning (offset = 0). This parameter is how paging is implemented, but
     * rather than page numbers it is strictly offsets.
     */
    fun getUpcomingLaunches(offset: Int? = null): Flowable<PagedLaunchSummary>

    fun getLaunch(id: Int): Flowable<Launch>

}

abstract class Page(
        /**
         * The offset a paged API call was made with.
         */
        val offset: Int,

        /**
         * The number of objects returned from a paged API call.
         */
        val count: Int,

        /**
         * The total number of available objects in a paged endpoint.
         */
        val total: Int
)

class PagedLaunchSummary(
        offset: Int,
        count: Int,
        total: Int,
        var launches: List<LaunchSummary> = listOf()
) : Page(offset, count, total), Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()
    ) {
        val tmpLaunches = mutableListOf<LaunchSummary>()
        parcel.readTypedList(tmpLaunches, LaunchSummary.CREATOR)
        this.launches = tmpLaunches
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(offset)
        parcel.writeInt(count)
        parcel.writeInt(total)
        parcel.writeTypedList(launches)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PagedLaunchSummary> {
        override fun createFromParcel(parcel: Parcel): PagedLaunchSummary {
            return PagedLaunchSummary(parcel)
        }

        override fun newArray(size: Int): Array<PagedLaunchSummary?> {
            return arrayOfNulls(size)
        }
    }

}

data class LaunchSummary(
        val id: Int,
        val name: String,
        val dateTime: LocalDateTime
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            LocalDateTime.parse(parcel.readString(), formatter)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(dateTime.format(formatter))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaunchSummary> {

        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        override fun createFromParcel(parcel: Parcel): LaunchSummary {
            return LaunchSummary(parcel)
        }

        override fun newArray(size: Int): Array<LaunchSummary?> {
            return arrayOfNulls(size)
        }

    }

}

class Launch(
        val id: Int,
        val name: String,
        val dateTime: LocalDateTime,
        val location: Location,
        val rocket: Rocket,
        var missions: List<Mission> = listOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            LocalDateTime.parse(parcel.readString(), formatter),
            Location(parcel.readString()!!),
            Rocket(parcel.readString()!!)
    ) {
        val missions = mutableListOf<Mission>()
        parcel.readTypedList(missions, Mission.CREATOR)

        this.missions = missions
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(dateTime.format(formatter))
        parcel.writeString(location.name)
        parcel.writeString(rocket.name)
        parcel.writeTypedList(missions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Launch> {

        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        override fun createFromParcel(parcel: Parcel): Launch {
            return Launch(parcel)
        }

        override fun newArray(size: Int): Array<Launch?> {
            return arrayOfNulls(size)
        }

    }

    data class Location(val name: String)

    data class Rocket(val name: String)

    data class Mission(val name: String) : Parcelable {
        constructor(parcel: Parcel) : this(parcel.readString()!!)

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Mission> {
            override fun createFromParcel(parcel: Parcel): Mission {
                return Mission(parcel)
            }

            override fun newArray(size: Int): Array<Mission?> {
                return arrayOfNulls(size)
            }
        }
    }

}
