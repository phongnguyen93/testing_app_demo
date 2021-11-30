package com.example.app.movie.data.source

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import io.reactivex.Completable
import io.reactivex.Flowable

class DefaultMovieRepository(
  private val localMovieDataSource: MovieDataSource,
  private val remoteMovieDataSource: MovieDataSource
) : MovieRepository {
  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    return localMovieDataSource.observeMovies().flatMap({
      if (it is Result.Success && !it.data.isNullOrEmpty()) {
        Flowable.just(it)
      } else {
        remoteMovieDataSource.observeMovies().doOnNext {
          if (it is Result.Success) {
            localMovieDataSource.addAllMovies(it.data).subscribe()
          }
        }
      }
    }, {
      remoteMovieDataSource.observeMovies().doAfterNext {
        if (it is Result.Success) localMovieDataSource.addAllMovies(it.data).subscribe()
      }
    }, {
      Flowable.empty()
    })
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    return localMovieDataSource.observeMovie(movieId)
  }

  override fun observeFavoriteMovies(): Flowable<Result<List<Movie>>> {
    return localMovieDataSource.observeFavoritedMovies()
  }

  override fun addToFavorite(movie: Movie): Completable {
    movie.favorited = 1
    return localMovieDataSource.updateMovie(movie)
  }

  override fun removeFromFavorite(movie: Movie): Completable {
    movie.favorited = 0
    return localMovieDataSource.updateMovie(movie)
  }
}