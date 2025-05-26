package com.example.data.repository

import com.example.data.MainDispatcherRule
import com.example.data.TheMovieDbApi
import com.example.data.model.MovieDTO
import com.example.data.model.MovieItem
import com.example.domain.repository.MovieRepository
import com.example.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImplTest {

 @get:Rule
 val mainDispatcherRule = MainDispatcherRule()

 private val movieDbApi: TheMovieDbApi = mockk()
 private lateinit var repository: MovieRepository

 @Before
 fun setUp() {
  repository = MovieRepositoryImpl(movieDbApi)
 }

 @Test
 fun `searchMovies emits Success when API returns successful response`() = runTest {
  val movieItem = MovieItem(
   backdropPath = "/test.jpg",
   mediaType = "movie",
   name = null,
   originalLanguage = "en",
   originalName = "Test Movie",
   originalTitle = "Test Movie",
   overview = "Overview",
   popularity = 5.0,
   posterPath = "/poster.jpg",
   releaseDate = "2024-01-01",
   title = "Test Movie",
   voteAverage = 8.5,
   voteCount = 100,
   id = 123,
   video = false,
   firstAirDate = null,
   originCountry = emptyList(),
   genreIds = emptyList(),
   adult = false,
  )

  val movieResponse = MovieDTO(movieItems = listOf(movieItem), page = 1, totalPages = 1, totalResults = 1)
  val response: Response<MovieDTO> = Response.success(movieResponse)

  coEvery { movieDbApi.searchMovies(any(),any(), any()) } returns response

  val results = repository.searchMovies("Batman", 1).toList()

  assert(results.first() is Result.Success)
  val success = results.first() as Result.Success
  assertEquals("Test Movie", success.data?.first()?.title)
 }

 @Test
 fun `searchMovies emits Failure when API returns error response`() = runTest {
  val errorResponse = Response.error<MovieDTO>(
   404,
   ResponseBody.create("application/json".toMediaTypeOrNull(), "Not found")
  )

  coEvery { movieDbApi.searchMovies(any(),any(), any()) } returns errorResponse

  val results = repository.searchMovies("Unknown", 1).toList()

  assert(results.first() is Result.Failure)
  val failure = results.first() as Result.Failure
  assertEquals(-111, failure.errorCode)
 }

 @Test
 fun `searchMovies emits Failure on IOException`() = runTest {
  coEvery { movieDbApi.searchMovies(any(),any(),any()) } throws IOException("No connection")

  val results = repository.searchMovies("NetworkFail", 1).toList()

  assert(results.first() is Result.Failure)
  val failure = results.first() as Result.Failure
  assertEquals(-100, failure.errorCode)
  assertEquals("No connection", failure.message)
 }
}
