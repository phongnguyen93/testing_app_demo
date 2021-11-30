package com.example.app.movie.movie_favorite

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.movie.R
import com.example.app.movie.data.Movie
import com.example.app.movie.movie_detail.MovieDetailFragment
import com.example.app.movie.ui.ItemAction
import com.example.app.movie.ui.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_favorite.*

@AndroidEntryPoint
class MovieFavoriteListFragment : Fragment(R.layout.fragment_movie_favorite) {
  private val viewModel: MovieFavoriteListViewModel by viewModels()

  private val adapter = MovieListAdapter(object : ItemAction {
    override fun onClick(item: Movie) {
      findNavController().navigate(
        R.id.movie_detail, bundleOf(
          MovieDetailFragment.MOVIE_ID to item.id
        )
      )
    }
  })

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    rv_movie_fav.layoutManager = LinearLayoutManager(context)
    rv_movie_fav.adapter = adapter

    viewModel.movies.observe(viewLifecycleOwner, {
      adapter.setData(it)
    })

    viewModel.loading.observe(viewLifecycleOwner, {
      if (it == true) {
        progress_bar.visibility = View.VISIBLE
      } else {
        progress_bar.visibility = View.GONE
      }
    })

    viewModel.fetchFavoritedMovies()
  }
}