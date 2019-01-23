package com.dpanger.android.launches.data.launches

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BackendSourceUnitTest {

    private lateinit var api: LaunchApi
    private lateinit var source: BackendSource

    @Before
    fun SetUp() {
        api = mock {
            on { getUpcomingLaunches(anyOrNull()) } doReturn Single.error(Exception())
            on { getLaunch(any(), any()) } doReturn Single.error(Exception())
        }

        source = BackendSource(api)
    }

    @Test
    fun GetUpcomingLaunches_DefaultOffset_ApiParametersSetCorrectly() {
        source.getUpcomingLaunches()

        verify(api).getUpcomingLaunches()
    }

    @Test
    fun GetUpcomingLaunches_SpecificOffset_ApiParametersSetCorrectly() {
        val offset = 7
        source.getUpcomingLaunches(offset)

        verify(api).getUpcomingLaunches(offset)
    }

    @Test
    fun GetLaunch_ApiParametersSetCorrectly() {
        val id = 102
        source.getLaunch(id)

        verify(api).getLaunch(LaunchApi.Mode.VERBOSE.mode, id)
    }

}
