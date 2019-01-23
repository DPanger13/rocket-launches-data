package com.dpanger.android.launches.data.launches

import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader

class ILaunchRepositoryUnitTest {

    private lateinit var backendSource: BackendSource
    private lateinit var repository: ILaunchRepository

    private lateinit var launchesSubscriber: TestSubscriber<PagedLaunchSummary>
    private lateinit var launchSubscriber: TestSubscriber<Launch>

    private lateinit var launchSummary: LaunchApi.PagedLaunchSummaryDto
    private lateinit var launch: LaunchApi.PagedLaunchDto

    @Before
    fun SetUp() {
        val gson = Gson()
        var inputStream = this.javaClass.getResourceAsStream("launches.json")
        var reader = InputStreamReader(inputStream)
        launchSummary = gson.fromJson(reader, LaunchApi.PagedLaunchSummaryDto::class.java)

        inputStream = this.javaClass.getResourceAsStream("launch.json")
        reader = InputStreamReader(inputStream)
        launch = gson.fromJson(reader, LaunchApi.PagedLaunchDto::class.java)

        backendSource = mock()
        repository = LaunchRepository(backendSource)

        launchesSubscriber = TestSubscriber()
        launchSubscriber = TestSubscriber()
    }

    //region getUpcomingLaunches()
    @Test
    fun GetUpcomingLaunches_OffsetNull_NullOffsetUsed() {
        val offset: Int? = null
        val backendFlowable = Single.error<LaunchApi.PagedLaunchSummaryDto>(Exception())
        whenever(backendSource.getUpcomingLaunches(offset)).thenReturn(backendFlowable)

        repository.getUpcomingLaunches(offset).subscribe(launchesSubscriber)

        verify(backendSource).getUpcomingLaunches(offset)
    }

    @Test
    fun GetUpcomingLaunches_OffsetNotNull_NotNullOffsetUsed() {
        val offset = 5
        val backendFlowable = Single.error<LaunchApi.PagedLaunchSummaryDto>(Exception())
        whenever(backendSource.getUpcomingLaunches(offset)).thenReturn(backendFlowable)

        repository.getUpcomingLaunches(offset).subscribe(launchesSubscriber)

        verify(backendSource).getUpcomingLaunches(offset)
    }

    @Test
    fun GetUpcomingLaunches_Success_LaunchesEmitted() {
        val offset: Int? = null
        val backendFlowable = Single.just(launchSummary)
        whenever(backendSource.getUpcomingLaunches(offset)).thenReturn(backendFlowable)

        repository.getUpcomingLaunches(offset).subscribe(launchesSubscriber)

        verify(backendSource).getUpcomingLaunches(offset)

        launchesSubscriber.assertValueCount(1)
    }

    @Test
    fun GetUpcomingLaunches_Error_ErrorEmitted() {
        val offset: Int? = null
        val backendFlowable = Single.error<LaunchApi.PagedLaunchSummaryDto>(Exception())
        whenever(backendSource.getUpcomingLaunches(offset)).thenReturn(backendFlowable)

        repository.getUpcomingLaunches(offset).subscribe(launchesSubscriber)

        verify(backendSource).getUpcomingLaunches(offset)

        launchesSubscriber.assertError { it is Exception }
    }
    //endregion

    //region getLaunch()
    @Test
    fun GetLaunch_IdUsedCorrectly() {
        val id = 1
        val backendFlowable = Single.error<LaunchApi.PagedLaunchDto>(Exception())
        whenever(backendSource.getLaunch(id)).thenReturn(backendFlowable)

        repository.getLaunch(id).subscribe(launchSubscriber)

        verify(backendSource).getLaunch(id)
    }

    @Test
    fun GetLaunch_Success_LaunchEmitted() {
        val movieId = 1
        val backendSingle = Single.just(launch)
        whenever(backendSource.getLaunch(movieId)).thenReturn(backendSingle)

        repository.getLaunch(movieId).subscribe(launchSubscriber)

        launchSubscriber.assertValueCount(1)
        launchSubscriber.assertNoErrors()
    }

    @Test
    fun GetLaunch_Error_ErrorEmitted() {
        val movieId = 1
        val backendSingle = Single.error<LaunchApi.PagedLaunchDto>(Exception())
        whenever(backendSource.getLaunch(movieId)).thenReturn(backendSingle)

        repository.getLaunch(movieId).subscribe(launchSubscriber)

        launchSubscriber.assertError { it is Exception }
    }
    //endregion

}
