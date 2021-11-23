package com.example.app.movie.data.source

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import io.reactivex.Flowable

class DefaultMovieRepository(
  private val localMovieDataSource: MovieDataSource,
  private val remoteMovieDataSource: MovieDataSource
) : MovieRepository {
  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    return remoteMovieDataSource.observeMovies()
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    return localMovieDataSource.observeMovie(movieId)
  }

  override fun observeFavoriteMovies(): Flowable<Result<List<Movie>>> {
    return localMovieDataSource.observeMovies()
  }

  override fun addToFavorite(movie: Movie) {
    localMovieDataSource.addToFavorite(movie)
  }

  override fun removeFromFavorite(movie: Movie) {
    localMovieDataSource.removeFromFavorite(movie)
  }
}