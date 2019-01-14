package io.mercury.android.movies.data.movies

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class BackendSourceUnitTest {

    private lateinit var api: GithubApi
    private lateinit var source: BackendSource

    @Before
    fun SetUp() {
        api = mock()

        source = BackendSource(api)
    }

    @Test
    fun GetTopMovies_GithubApiUsed() {
        source.getTopMovies()

        verify(api).getTopMovies()
    }

}
