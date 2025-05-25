package com.example.data

import com.example.data.model.MovieDTO
import com.example.utils.Constants.SEARCH_MOVIE_END_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET(SEARCH_MOVIE_END_URL)
    suspend fun searchMovies(
        @Header("Authorization") header: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZTRlNjc3NWE3MWE0ZTIxM2M2NDMwYjZmNGNkYjc0MCIsIm5iZiI6MTY2NjA2OTQxNC4yMDg5OTk5LCJzdWIiOiI2MzRlMzNhNjg5Zjc0OTAwN2Q2YTQwMjQiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.7lPYweQMDRctDOoBqsypEHDOS-GMRLORjkfNQWYAXvs",
        @Query("query") query:String,
        @Query("page") page: Int
    ): Response<MovieDTO>
}