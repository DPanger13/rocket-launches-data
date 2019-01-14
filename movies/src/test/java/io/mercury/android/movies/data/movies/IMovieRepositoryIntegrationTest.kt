package io.mercury.android.movies.data.movies

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader

class IMovieRepositoryIntegrationTest {

    private lateinit var api: GithubApi
    private lateinit var repository: IMovieRepository

    private lateinit var topMovieSubscriber: TestSubscriber<List<IMovieRepository.TopMovie>>

    @Before
    fun SetUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        api = mock()
        repository = MovieRepository(BackendSource(api))

        topMovieSubscriber = TestSubscriber()
    }

    @Test
    fun GetTopMovies_ValidJson_MoviesEmitted() {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream("top_movies.json")
        val reader = InputStreamReader(inputStream)
        val listType = object : TypeToken<List<GithubApi.TopMovieDto>>() {}.type
        val movieList = Gson().fromJson<List<GithubApi.TopMovieDto>>(reader, listType)
        whenever(api.getTopMovies()).thenReturn(Single.just(movieList))

        repository.getTopMovies().subscribe(topMovieSubscriber)

        val movies = topMovieSubscriber.values()[0]
        assertEquals(5, movies.size)

        val movie = movies[0]
        assertEquals(1, movie.rank)
        assertEquals("The Godfather", movie.title)
        assertEquals(1972, movie.year)

        val imdbInfo = movie.imdbInfo
        assertEquals("tt0068646", imdbInfo.id)
        assertEquals(9.2, imdbInfo.rating, 0.01)
        assertEquals(1106047, imdbInfo.votes)
        assertNotNull(movie.imdbInfo.imdbPage)

        assertNotNull(movie.poster)
    }

}
