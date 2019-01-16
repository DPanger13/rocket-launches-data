package io.mercury.android.movies.data.movies

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.io.InputStreamReader

class IMovieRepositoryIntegrationTest {

    private val gson = Gson()

    private lateinit var api: GithubApi
    private lateinit var repository: IMovieRepository

    @Before
    fun SetUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        api = mock()
        repository = MovieRepository(BackendSource(api))
    }

    @Test
    fun GetTopMovies_ValidJson_MoviesEmitted() {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream("top_movies.json")
        val reader = InputStreamReader(inputStream)
        val listType = object : TypeToken<List<GithubApi.TopMovieDto>>() {}.type
        val movieList = gson.fromJson<List<GithubApi.TopMovieDto>>(reader, listType)
        whenever(api.getTopMovies()).thenReturn(Single.just(movieList))

        val topMovieSubscriber = TestSubscriber<List<TopMovie>>()
        repository.getTopMovies().subscribe(topMovieSubscriber)

        val movies = topMovieSubscriber.values()[0]
        assertEquals(5, movies.size)

        val movie = movies[0]
        assertEquals(1, movie.rank)
        assertEquals("The Godfather", movie.title)
        assertEquals(1972, movie.year)

        val imdbInfo = movie.extendedImdbInfo
        assertEquals("tt0068646", imdbInfo.id)
        assertEquals(9.2, imdbInfo.rating, 0.01)
        assertEquals(1106047, imdbInfo.votes)
        assertNotNull(imdbInfo.imdbPage)

        assertNotNull(movie.poster)
    }

    @Test
    fun GetMovieInfo_ValidJson_MovieEmitted() {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream("movie.json")
        val reader = InputStreamReader(inputStream)
        val movieDto = gson.fromJson(reader, GithubApi.MovieInfoDto::class.java)
        whenever(api.getMovieInfo(anyString())).thenReturn(Single.just(movieDto))

        val infoSubscriber = TestSubscriber<DetailedMovieInfo>()
        repository.getMovieInfo("imdbId").subscribe(infoSubscriber)

        val movieInfo = infoSubscriber.values()[0]
        val imdbInfo = movieInfo.imdbInfo
        assertEquals(9.2, imdbInfo.rating, 0.01)
        assertEquals(1106047, imdbInfo.votes)
        assertEquals("tt0068646", imdbInfo.id)
        assertEquals("The Godfather", movieInfo.title)
        assertEquals(1972, movieInfo.year)
        assertEquals(DetailedMovieInfo.ContentRating.R, movieInfo.contentRating)
        assertNotNull(movieInfo.releaseDate)
        assertEquals(175, movieInfo.runtimeInMinutes)

        assertTrue(movieInfo.genres.contains("Crime"))
        assertTrue(movieInfo.genres.contains("Drama"))

        assertEquals("Francis Ford Coppola", movieInfo.director)

        assertTrue(movieInfo.writers.contains("Mario Puzo (screenplay)"))
        assertTrue(movieInfo.writers.contains("Francis Ford Coppola (screenplay)"))
        assertTrue(movieInfo.writers.contains("Mario Puzo (novel)"))

        assertTrue(movieInfo.actors.size == 1)
        assertNotNull(movieInfo.plot)

        assertTrue(movieInfo.languages.contains("English"))
        assertTrue(movieInfo.languages.contains("Italian"))
        assertTrue(movieInfo.languages.contains("Latin"))

        assertEquals("USA", movieInfo.country)
        assertNotNull(movieInfo.awards)
        assertNotNull(movieInfo.poster)
        assertEquals(100, movieInfo.metascore)
    }

}
