package io.mercury.android.movies.data.movies

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class BackendSourceUnitTest {

    private lateinit var api: GithubApi
    private lateinit var source: BackendSource

    @Before
    fun SetUp() {
        api = mock {
            on { getMovieInfo(anyString()) } doReturn Single.error(Exception())
            on { getTopMovies() } doReturn Single.error(Exception())
        }

        source = BackendSource(api)
    }

    @Test
    fun GetTopMovies_GithubApiUsed() {
        source.getTopMovies()

        verify(api).getTopMovies()
    }

    @Test
    fun GetImdbMovie_GithubApiUsed() {
        val id = "tt102"
        source.getMovieInfo(id)

        verify(api).getMovieInfo(id)
    }

}
