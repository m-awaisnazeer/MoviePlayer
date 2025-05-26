package com.example.searchmovie

import app.cash.turbine.test
import com.example.domain.model.Movie
import com.example.domain.repository.MovieRepository
import com.example.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val fakeMovie = Movie(
        backdropPath = "/path/to/backdrop.jpg",
        mediaType = "movie",
        name = "Fake Movie Name",
        originalLanguage = "en",
        originalName = "Fake Original Name",
        originalTitle = "Fake Original Title",
        overview = "This is a fake overview for the unit test.",
        popularity = 123.45,
        posterPath = "/path/to/poster.jpg",
        releaseDate = "2023-01-01",
        title = "Fake Movie Title",
        voteAverage = 8.7,
        voteCount = 999
    )

    private lateinit var viewModel: SearchViewModel
    private val repository: MovieRepository = mockk()

    @Before
    fun setup() {
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `initial state should be Initial`() = runTest {
        assertEquals(SearchMovieUiState.Initial, viewModel.state.value)
    }

    @Test
    fun `empty query should reset to Initial`() = runTest {
        viewModel.updateQuery("")
        assertEquals(SearchMovieUiState.Initial, viewModel.state.value)
    }

    @Test
    fun `updateQuery with valid result returns Success state`() = runTest {
        val movieList = listOf(fakeMovie)
        coEvery { repository.searchMovies("Fake Movie", 1) } returns flow {
            emit(Result.Success(movieList, 200))
        }

        viewModel.updateQuery("Fake Movie")


        viewModel.state.test {
            assertEquals(SearchMovieUiState.Loading, awaitItem())
            assertEquals(SearchMovieUiState.Success(movieList), awaitItem())
        }
    }

    @Test
    fun `updateQuery returns NoResultFound if list is empty`() = runTest {
        coEvery { repository.searchMovies("Unknown", 1) } returns flow {
            emit(Result.Success(emptyList(), 200))
        }

        viewModel.updateQuery("Unknown")

        viewModel.state.test {
            assertEquals(SearchMovieUiState.Loading, awaitItem())
            assertEquals(SearchMovieUiState.NoResultFound, awaitItem())
        }
    }

    @Test
    fun `updateQuery returns Error on failure`() = runTest {
        val errorMsg = "Network Error"
        coEvery { repository.searchMovies("Error", 1) } returns flow {
            emit(Result.Failure(errorMsg, 400))
        }

        viewModel.updateQuery("Error")

        viewModel.state.test {
            assertEquals(SearchMovieUiState.Loading, awaitItem())
            val error: SearchMovieUiState.Error = awaitItem() as SearchMovieUiState.Error
            assertEquals(errorMsg,error.message)
        }
    }
}
