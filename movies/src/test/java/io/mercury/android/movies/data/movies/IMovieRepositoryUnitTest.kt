package io.mercury.android.movies.data.movies

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.mercury.android.movies.data.movies.GithubApi.MovieInfoDto
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.mercury.android.movies.data.movies.IMovieRepository.TopMovie
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

class IMovieRepositoryUnitTest {

    private lateinit var backendSource: BackendSource
    private lateinit var repository: IMovieRepository

    private lateinit var topMovieSubscriber: TestSubscriber<List<TopMovie>>
    private lateinit var movieInfoSubscriber: TestSubscriber<IMovieRepository.DetailedMovieInfo>

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

        topMovieSubscriber = TestSubscriber()
        movieInfoSubscriber = TestSubscriber()
    }

    //region getTopMovies()
    @Test
    fun GetTopMovies_Success_MoviesEmitted() {
        val backendFlowable = Single.just(backendMovies)
        whenever(backendSource.getTopMovies()).thenReturn(backendFlowable)

        repository.getTopMovies().subscribe(topMovieSubscriber)

        verify(backendSource).getTopMovies()

        topMovieSubscriber.assertValueCount(1)
    }

    @Test
    fun GetTopMovies_Error_ErrorEmitted() {
        val backendFlowable = Single.error<List<TopMovieDto>>(Exception())
        whenever(backendSource.getTopMovies()).thenReturn(backendFlowable)

        repository.getTopMovies().subscribe(topMovieSubscriber)

        verify(backendSource).getTopMovies()

        topMovieSubscriber.assertError { it is Exception }
    }

    @Test
    fun GetMovieInfo_Success_MovieEmitted() {
        val movieId = "tt0001"
        val movie = GithubApi.MovieInfoDto(
                0.0,
                2,
                movieId,
                "title",
                1990,
                "R",
                "24 Mar 1908",
                "175 min",
                listOf("Drama"),
                "director",
                "writers",
                listOf("someone"),
                "plot",
                listOf("Chinese"),
                "China",
                "awards",
                "example.com",
                "100"
        )
        val backendSingle = Single.just(movie)
        whenever(backendSource.getMovieInfo(movieId)).thenReturn(backendSingle)

        repository.getMovieInfo(movieId).subscribe(movieInfoSubscriber)

        movieInfoSubscriber.assertValueCount(1)
        movieInfoSubscriber.assertNoErrors()
    }

    @Test
    fun GetMovieInfo_Error_ErrorEmitted() {
        val movieId = "tt1234"
        val backendSingle = Single.error<MovieInfoDto>(Exception())
        whenever(backendSource.getMovieInfo(movieId)).thenReturn(backendSingle)

        repository.getMovieInfo(movieId).subscribe(movieInfoSubscriber)

        movieInfoSubscriber.assertError { it is Exception }
    }
    //endregion

}
