package com.example.app.movie.data.source.local

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieDataSource
import com.example.app.movie.util.uiSubscribe
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalMovieDataSource(
  private val movieDao: MovieDao
) : MovieDataSource {
  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    return movieDao.observeMovies().uiSubscribe().flatMap({
      Flowable.just(Result.Success(it))
    }, {
      Flowable.just(Result.Error(it))
    }, {
      Flowable.empty()
    })
  }

  override fun observeFavoritedMovies(): Flowable<Result<List<Movie>>> {
    return movieDao.observeFavoritedMovies().uiSubscribe().flatMap({
      Flowable.just(Result.Success(it))
    }, {
      Flowable.just(Result.Error(it))
    }, {
      Flowable.empty()
    })
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    return movieDao.observeMovieById(movieId).uiSubscribe().flatMap({
      Flowable.just(Result.Success(it))
    }, {
      Flowable.just(Result.Error(it))
    }, {
      Flowable.empty()
    })
  }

  override fun updateMovie(movie: Movie): Completable {
    return movieDao.updateMovie(movie).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

  override fun addAllMovies(movie: List<Movie>): Completable {
    return movieDao.insertMovies(movie).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }

}