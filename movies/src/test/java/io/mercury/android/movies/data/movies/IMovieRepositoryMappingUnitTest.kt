package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class IMovieRepositoryMappingUnitTest {

    @Test
    fun DtoToTopMovie_RankCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        assertEquals(RANK, topMovie.rank)
    }

    @Test
    fun DtoToTopMovie_TitleCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        assertEquals(TITLE, topMovie.title)
    }

    @Test
    fun DtoToTopMovie_YearCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        assertEquals(YEAR, topMovie.year)
    }

    @Test
    fun DtoToTopMovie_PosterLinkCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        assertEquals(POSTER_LINK, topMovie.poster.toString())
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_IdCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.imdbInfo
        assertEquals(IMDB_ID, imdbInfo.id)
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_RatingCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.imdbInfo
        assertEquals(RATING, imdbInfo.rating, 0.01)
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_VotesCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.imdbInfo
        assertEquals(VOTES, imdbInfo.votes)
        assertEquals(IMDB_PAGE, imdbInfo.imdbPage.toString())
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_PageCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.imdbInfo
        assertEquals(IMDB_PAGE, imdbInfo.imdbPage.toString())
    }

    private fun mapDefaultTopMovieDto() =
            TopMovieDto(
                    RANK,
                    TITLE,
                    YEAR,
                    IMDB_ID,
                    RATING,
                    VOTES,
                    POSTER_LINK,
                    IMDB_PAGE
            ).toTopMovie()

    private companion object {
        private const val RANK = 1
        private const val TITLE = "some movie"
        private const val YEAR = 1990
        private const val RATING = 8.2
        private const val VOTES = 7
        private const val IMDB_ID = "10"
        private const val IMDB_PAGE = "imdb.com"
        private const val POSTER_LINK = "example.com"
    }

}
