package com.example.app.movie.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.movie.data.Movie
import com.example.app.movie.data.Result
import com.example.app.movie.data.source.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
  private val movieRepository: MovieRepository
) : ViewModel() {

  private var compositeDisposable = CompositeDisposable()

  private val _movies = MutableLiveData<List<Movie>>()
  val movies: LiveData<List<Movie>> = _movies

  private val _loading = MutableLiveData<Boolean>()
  val loading: LiveData<Boolean> = _loading

  fun fetchMovies() {
    _loading.value = true
    val disp = movieRepository.observeMovies().subscribe({
      if (it is Result.Success) {
        _loading.value = false
      }
    }, {
      _loading.value = false
    })
    compositeDisposable.add(disp)
  }

}