package com.example.app.movie.data.source

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Main entry point for accessing movies data.
 */
interface MovieDataSource {
  fun observeMovies(): Flowable<Result<List<Movie>>>

  fun observeFavoritedMovies(): Flowable<Result<List<Movie>>>

  fun observeMovie(movieId: Int): Flowable<Result<Movie>>

  fun updateMovie(movie: Movie): Completable

  fun addAllMovies(movie: List<Movie>): Completable
}