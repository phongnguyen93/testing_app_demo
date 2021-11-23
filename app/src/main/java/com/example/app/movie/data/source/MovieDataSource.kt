package com.example.app.movie.data.source

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import io.reactivex.Flowable

/**
 * Main entry point for accessing movies data.
 */
interface MovieDataSource {
  fun observeMovies(): Flowable<Result<List<Movie>>>

  fun observeMovie(movieId: Int): Flowable<Result<Movie>>

  fun addToFavorite(movie: Movie)

  fun removeFromFavorite(movie: Movie)
}