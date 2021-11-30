package com.example.app.movie.movie_favorite

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieRepository
import com.example.app.movie.util.wrapAsyncEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieFavoriteListViewModel @Inject constructor(
  private val movieRepository: MovieRepository
) : ViewModel() {

  private var compositeDisposable = CompositeDisposable()

  private val _movies = MutableLiveData<List<Movie>>()
  val movies: LiveData<List<Movie>> = _movies

  private val _loading = MutableLiveData(true)
  val loading: LiveData<Boolean> = _loading

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
    compositeDisposable.clear()
  }

  @SuppressLint("NullSafeMutableLiveData")
  fun fetchFavoritedMovies() {
    wrapAsyncEspressoIdlingResource { idlingResource ->
      idlingResource.increment()
      val disp = movieRepository.observeFavoriteMovies().subscribe({
        if (it is Result.Success) {
          _loading.value = false
          _movies.value = it.data
          idlingResource.decrement()
        }
      }, {
        _loading.value = false
        idlingResource.decrement()
      })
      compositeDisposable.add(disp)
    }


  }

}