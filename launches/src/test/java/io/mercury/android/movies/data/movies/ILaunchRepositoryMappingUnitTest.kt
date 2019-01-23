package io.mercury.android.movies.data.movies

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.InputStreamReader

class ILaunchRepositoryMappingUnitTest {

    private val gson = Gson()

    //region PagedLaunchSummary mapping
    @Test
    fun DtoToPagedLaunchSummary_OffsetCorrect() {
        val dto = mapDefaultPagedLaunchSummaryDto()

        assertEquals(0, dto.offset)
    }

    @Test
    fun DtoToPagedLaunchSummary_CountCorrect() {
        val dto = mapDefaultPagedLaunchSummaryDto()

        assertEquals(5, dto.count)
    }

    @Test
    fun DtoToPagedLaunchSummary_TotalCorrect() {
        val dto = mapDefaultPagedLaunchSummaryDto()

        assertEquals(185, dto.total)
    }

    @Test
    fun DtoToPagedLaunchSummary_LaunchesCorrect() {
        val dto = mapDefaultPagedLaunchSummaryDto()

        val launch = dto.launches[0]
        assertEquals(1288, launch.id)
        assertEquals("Delta IV Heavy | NROL-71", launch.name)
        assertNotNull(launch.dateTime)
    }

    private fun mapDefaultPagedLaunchSummaryDto(): PagedLaunchSummary {
        val reader = InputStreamReader(this.javaClass.getResourceAsStream("launches.json"))
        val launches = gson.fromJson(reader, LaunchApi.PagedLaunchSummaryDto::class.java)

        return launches.toPagedLaunchSummary()
    }
    //endregion

    //region LaunchSummary mapping
    @Test
    fun DtoToLaunchSummary_IdCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertEquals(1288, launch.id)
    }

    @Test
    fun DtoToLaunchSummary_NameCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertEquals("Delta IV Heavy | NROL-71", launch.name)
    }

    @Test
    fun DtoToLaunchSummary_DateTimeCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertNotNull(launch.dateTime)
    }

    @Test
    fun DtoToLaunchSummary_LocationCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertEquals("Vandenberg AFB, CA, USA", launch.location.name)
    }

    @Test
    fun DtoToLaunchSummary_RocketCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertEquals("Delta IV Heavy", launch.rocket.name)
    }

    @Test
    fun DtoToLaunchSummary_MissionsCorrect() {
        val launch = mapDefaultPagedLaunchDto()

        assertEquals("NROL-71", launch.missions[0].name)
    }

    private fun mapDefaultPagedLaunchDto(): Launch {
        val reader = InputStreamReader(this.javaClass.getResourceAsStream("launch.json"))
        val pagedLaunchDto = gson.fromJson(reader, LaunchApi.PagedLaunchDto::class.java)

        return pagedLaunchDto.toLaunch()
    }
    //endregion

}
