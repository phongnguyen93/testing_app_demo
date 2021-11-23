package com.example.app.movie.data.source.local

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieDataSource
import com.example.app.movie.util.uiSubscribe
import com.example.app.movie.util.wrapEspressoIdlingResource
import io.reactivex.Flowable

class LocalMovieDataSource(
  private val movieDao: MovieDao
) : MovieDataSource {
  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    wrapEspressoIdlingResource {
      return movieDao.observeMovies().uiSubscribe().map { Result.Success(it) }
    }
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    wrapEspressoIdlingResource {
      return movieDao.observeMovieById(movieId).uiSubscribe().map { Result.Success(it) }
    }
  }

  override fun addToFavorite(movie: Movie) {
    movieDao.insertMovie(movie)
  }

  override fun removeFromFavorite(movie: Movie) {
    movieDao.deleteMovieById(movie.id)
  }

}