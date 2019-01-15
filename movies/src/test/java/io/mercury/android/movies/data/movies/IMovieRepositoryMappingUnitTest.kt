package io.mercury.android.movies.data.movies

import io.mercury.android.movies.data.movies.GithubApi.MovieInfoDto
import io.mercury.android.movies.data.movies.GithubApi.TopMovieDto
import io.mercury.android.movies.data.movies.IMovieRepository.DetailedMovieInfo.ContentRating
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate

class IMovieRepositoryMappingUnitTest {

    //region TopMovie mapping
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

        val imdbInfo = topMovie.extendedImdbInfo
        assertEquals(IMDB_ID, imdbInfo.id)
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_RatingCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.extendedImdbInfo
        assertEquals(RATING, imdbInfo.rating, 0.01)
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_VotesCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.extendedImdbInfo
        assertEquals(VOTES, imdbInfo.votes)
        assertEquals(IMDB_PAGE, imdbInfo.imdbPage.toString())
    }

    @Test
    fun DtoToTopMovie_ImdbInfo_PageCorrect() {
        val topMovie = mapDefaultTopMovieDto()

        val imdbInfo = topMovie.extendedImdbInfo
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
    //endregion

    //region DetailedMovieInfo mapping
    @Test
    fun DtoToDetailedInfo_TitleCorrect() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(TITLE, info.title)
    }

    @Test
    fun DtoToDetailedInfo_YearCorrect() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(YEAR, info.year)
    }

    @Test
    fun DtoToDetailedInfo_PosterLinkCorrect() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(POSTER_LINK, info.poster.toString())
    }

    @Test
    fun DtoToDetailedInfo_ImdbInfo_IdCorrect() {
        val info = mapDefaultMovieInfoDto()

        val imdbInfo = info.imdbInfo
        assertEquals(IMDB_ID, imdbInfo.id)
    }

    @Test
    fun DtoToDetailedInfo_ImdbInfo_RatingCorrect() {
        val info = mapDefaultMovieInfoDto()

        val imdbInfo = info.imdbInfo
        assertEquals(RATING, imdbInfo.rating, 0.01)
    }

    @Test
    fun DtoToDetailedInfo_ImdbInfo_VotesCorrect() {
        val info = mapDefaultMovieInfoDto()

        val imdbInfo = info.imdbInfo
        assertEquals(VOTES, imdbInfo.votes)
    }

    @Test
    fun DtoToDetailedInfo_ContentRating_R_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(ContentRating.R, info.contentRating)
    }

    @Test
    fun DtoToDetailedInfo_ContentRating_Invalid_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                "T",
                RELEASE_DATE,
                RUNTIME,
                GENRES,
                DIRECTOR,
                WRITERS,
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                METASCORE
        ).toDetailedMovieInfo()

        assertEquals(ContentRating.UNKNOWN, info.contentRating)
    }

    @Test
    fun DtoToDetailedInfo_ReleaseDate_Valid_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        val expected = LocalDate.of(2018, 3, 24)
        assertEquals(expected, info.releaseDate)
    }

    @Test
    fun DtoToDetailedInfo_ReleaseDate_Invalid_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                CONTENT_RATING,
                "2018-01-14",
                RUNTIME,
                GENRES,
                DIRECTOR,
                WRITERS,
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                METASCORE
        ).toDetailedMovieInfo()

        assertNull(info.releaseDate)
    }

    @Test
    fun DtoToDetailedInfo_Runtime_Valid_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(175, info.runtimeInMinutes)
    }

    @Test
    fun DtoToDetailedInfo_Runtime_Invalid_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                CONTENT_RATING,
                RELEASE_DATE,
                "1 hr 20 min",
                GENRES,
                DIRECTOR,
                WRITERS,
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                METASCORE
        ).toDetailedMovieInfo()

        assertNull(info.runtimeInMinutes)
    }

    @Test
    fun DtoToDetailedInfo_Genres_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(GENRES, info.genres)
    }

    @Test
    fun DtoToDetailedInfo_Director_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(DIRECTOR, info.director)
    }

    @Test
    fun DtoToDetailedInfo_Writers_Multiple_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(listOf("someone", "someone2", "someone3"), info.writers)
    }

    @Test
    fun DtoToDetailedInfo_Writers_Single_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                CONTENT_RATING,
                RELEASE_DATE,
                RUNTIME,
                GENRES,
                DIRECTOR,
                "someone",
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                METASCORE
        ).toDetailedMovieInfo()

        assertEquals(listOf("someone"), info.writers)
    }

    @Test
    fun DtoToDetailedInfo_Writers_Empty_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                CONTENT_RATING,
                RELEASE_DATE,
                RUNTIME,
                GENRES,
                DIRECTOR,
                "",
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                METASCORE
        ).toDetailedMovieInfo()

        assertTrue(info.writers.isEmpty())
    }

    @Test
    fun DtoToDetailedInfo_Actors_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(ACTORS, info.actors)
    }

    @Test
    fun DtoToDetailedInfo_Plot_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(PLOT, info.plot)
    }

    @Test
    fun DtoToDetailedInfo_Languages_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(LANGUAGES, info.languages)
    }

    @Test
    fun DtoToDetailedInfo_Country_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(COUNTRY, info.country)
    }

    @Test
    fun DtoToDetailedInfo_Awards_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(AWARDS, info.awards)
    }

    @Test
    fun DtoToDetailedInfo_Metascore_Valid_MappedCorrectly() {
        val info = mapDefaultMovieInfoDto()

        assertEquals(100, info.metascore)
    }

    @Test
    fun DtoToDetailedInfo_Metascore_Invalid_MappedCorrectly() {
        val info = MovieInfoDto(
                RATING,
                VOTES,
                IMDB_ID,
                TITLE,
                YEAR,
                CONTENT_RATING,
                RELEASE_DATE,
                RUNTIME,
                GENRES,
                DIRECTOR,
                WRITERS,
                ACTORS,
                PLOT,
                LANGUAGES,
                COUNTRY,
                AWARDS,
                POSTER_LINK,
                "1r5"
        ).toDetailedMovieInfo()

        assertNull(info.metascore)
    }

    private fun mapDefaultMovieInfoDto() =
            MovieInfoDto(
                    RATING,
                    VOTES,
                    IMDB_ID,
                    TITLE,
                    YEAR,
                    CONTENT_RATING,
                    RELEASE_DATE,
                    RUNTIME,
                    GENRES,
                    DIRECTOR,
                    WRITERS,
                    ACTORS,
                    PLOT,
                    LANGUAGES,
                    COUNTRY,
                    AWARDS,
                    POSTER_LINK,
                    METASCORE
            ).toDetailedMovieInfo()
    //endregion

    private companion object {
        private val ACTORS = listOf("actor1", "actor2")
        private const val AWARDS = "some awards"
        private const val CONTENT_RATING = "R"
        private const val COUNTRY = "USA"
        private const val DIRECTOR = "director"
        private val GENRES = listOf("Drama")
        private val LANGUAGES = listOf("English", "Chinese")
        private const val METASCORE = "100"
        private const val PLOT = "some plot"
        private const val RANK = 1
        private const val RELEASE_DATE = "24 Mar 2018"
        private const val RUNTIME = "175 min"
        private const val TITLE = "some movie"
        private const val WRITERS = "someone, someone2, someone3"
        private const val YEAR = 1990
        private const val RATING = 8.2
        private const val VOTES = 7
        private const val IMDB_ID = "10"
        private const val IMDB_PAGE = "imdb.com"
        private const val POSTER_LINK = "example.com"
    }

}
