package io.mercury.android.movies.data.movies

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import java.io.InputStreamReader

class ILaunchRepositoryIntegrationTest {

    private val gson = Gson()

    private lateinit var api: LaunchApi
    private lateinit var repository: ILaunchRepository

    @Before
    fun SetUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        api = mock()
        repository = LaunchRepository(BackendSource(api))
    }

    @Test
    fun GetUpcomingLaunches_ValidJson_LaunchesEmitted() {
        val inputStream = this.javaClass.getResourceAsStream("launches.json")
        val reader = InputStreamReader(inputStream)
        val launchSummariesDto = gson.fromJson(reader, LaunchApi.PagedLaunchSummaryDto::class.java)
        whenever(api.getUpcomingLaunches(anyOrNull())).thenReturn(Single.just(launchSummariesDto))

        val launchesSubscriber = TestSubscriber<PagedLaunchSummary>()
        repository.getUpcomingLaunches(null).subscribe(launchesSubscriber)

        val launchSummaries = launchesSubscriber.values()[0]
        assertEquals(0, launchSummaries.offset)
        assertEquals(5, launchSummaries.count)
        assertEquals(185, launchSummaries.total)
        assertEquals(5, launchSummaries.launches.size)

        val launch = launchSummaries.launches[0]
        assertEquals(1288, launch.id)
        assertEquals("Delta IV Heavy | NROL-71", launch.name)
        assertNotNull(launch.dateTime)
    }

    @Test
    fun GetLaunch_ValidJson_LaunchEmitted() {
        val id = 1288
        val inputStream = this.javaClass.getResourceAsStream("launch.json")
        val reader = InputStreamReader(inputStream)
        val pagedLaunchDto = gson.fromJson(reader, LaunchApi.PagedLaunchDto::class.java)
        whenever(api.getLaunch(LaunchApi.Mode.VERBOSE.mode, id))
                .thenReturn(Single.just(pagedLaunchDto))

        val launchSubscriber = TestSubscriber<Launch>()
        repository.getLaunch(id).subscribe(launchSubscriber)

        val launch = launchSubscriber.values()[0]
        assertEquals(id, launch.id)
        assertEquals("Delta IV Heavy | NROL-71", launch.name)
        assertNotNull(launch.dateTime)

        val location = launch.location
        assertEquals("Vandenberg AFB, CA, USA", location.name)

        val rocket = launch.rocket
        assertEquals("Delta IV Heavy", rocket.name)

        val missions = launch.missions
        assertEquals(1, missions.size)

        var foundMission = false
        for (mission in missions) {
            if (mission.name == "NROL-71") foundMission = true
        }
        assertTrue(foundMission)
    }

}
