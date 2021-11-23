package com.example.app.movie.data.source.remote

import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieDataSource
import com.example.app.movie.util.uiSubscribe
import com.example.app.movie.util.wrapEspressoIdlingResource
import io.reactivex.Flowable

class RemoteMovieDataSource constructor(
  private val movieService: MovieService
) : MovieDataSource {

  override fun observeMovies(): Flowable<Result<List<Movie>>> {
    wrapEspressoIdlingResource {
      return movieService.fetchMovies().uiSubscribe()
        .map { Result.Success(it.results) }
    }
  }

  override fun observeMovie(movieId: Int): Flowable<Result<Movie>> {
    wrapEspressoIdlingResource {
      return movieService.fetchMovies().uiSubscribe()
        .map { Result.Success(it.results[0]) }
    }
  }

  override fun addToFavorite(movie: Movie) {
    //NO OP
  }

  override fun removeFromFavorite(movie: Movie) {
    //NO OP
  }


}