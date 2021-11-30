package com.example.app.movie.data.source.remote

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieDataSource
import com.example.app.movie.util.uiSubscribe
import io.reactivex.Completable
import io.reactivex.Flowable

class RemoteMovieDataSource constructor(
  private val movieService: MovieService
) : MovieDataSource {

  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    return movieService.fetchMovies().uiSubscribe().flatMap({
      Flowable.just(Result.Success(it.results))
    }, {
      Flowable.just(Result.Error(it))
    }, {
      Flowable.empty()
    })
  }

  override fun observeFavoritedMovies(): Flowable<Result<List<Movie>>> {
    return Flowable.empty()
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    return movieService.fetchMovieDetail(movieId).uiSubscribe().flatMap({
      Flowable.just(Result.Success(it))
    }, {
      Flowable.just(Result.Error(it))
    }, {
      Flowable.empty()
    })
  }


  override fun updateMovie(movie: Movie): Completable {
    return Completable.complete()
  }

  override fun addAllMovies(movie: List<Movie>): Completable {
    return Completable.complete()
  }


}