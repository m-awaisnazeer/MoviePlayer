package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.MovieRepository
import com.example.data.model.MovieItem
import com.example.utils.Result
import kotlinx.coroutines.flow.first

class MoviePagingSource(
  private val repository: MovieRepository,
  private val searchedQuery: String
) : PagingSource<Int, MovieItem>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
    return try {
      val page = params.key ?: 1
      val response = repository.searchMovies(searchedQuery,page)
      when (val result = response.first()) {
        is Result.Success -> {
          val data = result.data?.movieItems ?: emptyList()
          val nextKey =
            if (data.isEmpty()) {
              null
            } else {
              page + 1
            }
          LoadResult.Page(data = data, prevKey = null, nextKey = nextKey)
        }
        is Result.Failure -> {
          LoadResult.Error(Exception(result.message))
        }
      }
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
}
