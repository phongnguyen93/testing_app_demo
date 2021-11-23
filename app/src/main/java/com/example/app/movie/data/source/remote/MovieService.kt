package com.example.app.movie.data.source.remote

import com.example.app.movie.data.MovieListResponse
import com.example.app.movie.util.ServiceConst
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

  @GET("movie/popular")
  fun fetchMovies(
    @Query("api_key") apiKey: String = ServiceConst.API_KEY,
    @Query("page") page: Int = 1,
    @Query("limit") limit: Int = 100
  ): Flowable<MovieListResponse>

}