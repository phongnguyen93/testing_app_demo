package com.example.app.movie.utils

import com.example.app.movie.data.Movie
import com.example.app.movie.data.MovieListResponse
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieDataSource
import io.reactivex.Completable
import io.reactivex.Flowable

class TestMovieDataSource : MovieDataSource {

  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    val parsedObject = parseJsonToObject(TestData.rawJson, MovieListResponse::class.java)
    val testMovies = parsedObject.results
    return Flowable.just(Result.Success(testMovies))
  }

  override fun observeFavoritedMovies(): Flowable<Result<List<Movie>>> {
    return Flowable.empty()
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    val parsedObject = parseJsonToObject(TestData.rawJson, MovieListResponse::class.java)
    val testMovie = parsedObject.results.find { it.id == movieId }
    return Flowable.just(Result.Success(testMovie!!))
  }


  override fun updateMovie(movie: Movie): Completable {
    return Completable.complete()
  }

  override fun addAllMovies(movie: List<Movie>): Completable {
    return Completable.complete()
  }


}