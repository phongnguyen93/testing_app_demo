package com.example.app.movie.data.source

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface to the data layer.
 */
interface MovieRepository {

  fun observeMovies(): Flowable<Result<List<Movie>>>

  fun observeMovie(movieId: Int): Flowable<Result<Movie>>

  fun observeFavoriteMovies(): Flowable<Result<List<Movie>>>

  fun addToFavorite(movie: Movie): Completable

  fun removeFromFavorite(movie: Movie): Completable
}