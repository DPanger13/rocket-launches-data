package io.mercury.android.movies.data.movies

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

class IMovieRepositoryUnitTest {

    private lateinit var backendSource: BackendSource
    private lateinit var repository: IMovieRepository

    private lateinit var subscriber: TestSubscriber<List<TopMovie>>

    private val backendMovies by lazy {
        val dto = TopMovieDto(
                1,
                "some movie",
                1001,
                "10",
                8.2,
                7,
                "example.com",
                "imdb.com"
        )
        listOf(dto)
    }

    @Before
    fun SetUp() {
        backendSource = mock()
        repository = MovieRepository(backendSource)

        subscriber = TestSubscriber()
    }

    //region getTopMovies()
    @Test
    fun GetTopMovies_Success_MoviesEmitted() {
        val backendFlowable = Single.just(backendMovies)
        whenever(backendSource.getTopMovies()).thenReturn(backendFlowable)

        repository.getTopMovies().subscribe(subscriber)

        verify(backendSource).getTopMovies()

        subscriber.assertValueCount(1)
    }

    @Test
    fun GetTopMovies_Error_ErrorEmitted() {
        val backendFlowable = Single.error<List<TopMovieDto>>(Exception())
        whenever(backendSource.getTopMovies()).thenReturn(backendFlowable)

        repository.getTopMovies().subscribe(subscriber)

        verify(backendSource).getTopMovies()

        subscriber.assertError { it is Exception }
    }
    //endregion

}
