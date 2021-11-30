package com.example.app.movie.movie_detail

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
class MovieDetailViewModel @Inject constructor(
  private val movieRepository: MovieRepository
) : ViewModel() {

  private var compositeDisposable = CompositeDisposable()

  private val _movie = MutableLiveData<Movie>()
  val movie: LiveData<Movie> = _movie

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
    compositeDisposable.clear()
  }

  @SuppressLint("NullSafeMutableLiveData")
  fun fetchMovieDetail(id: Int) {
    wrapAsyncEspressoIdlingResource { idlingRes ->
      idlingRes.increment()
      val disp = movieRepository.observeMovie(id).subscribe({
        if (it is Result.Success) {
          _loading.value = false
          _movie.value = it.data
          idlingRes.decrement()
        }
      }, {
        _loading.value = false
        idlingRes.decrement()
      })
      compositeDisposable.add(disp)
    }
  }

  fun addToFav() {
    _movie.value?.let {
      movieRepository.addToFavorite(it).subscribe {
        fetchMovieDetail(it.id)
      }
    }
  }

  fun removeFromFav() {
    _movie.value?.let {
      movieRepository.removeFromFavorite(it).subscribe {
        fetchMovieDetail(it.id)
      }
    }
  }

}