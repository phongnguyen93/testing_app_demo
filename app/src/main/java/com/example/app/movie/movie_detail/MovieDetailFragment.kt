package com.example.app.movie.movie_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.app.movie.R
import com.example.app.movie.util.composeBackdropImg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

  companion object {
    const val MOVIE_ID = "MOVIE_ID"
  }

  private val viewModel: MovieDetailViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val movieId = arguments?.getInt(MOVIE_ID) ?: -1
    if (movieId != -1) viewModel.fetchMovieDetail(movieId)

    viewModel.movie.observe(viewLifecycleOwner, {
      tv_movie_detail_title.text = it.title
      tv_movie_detail_desc.text = it.overview
      setupFavButton(it.favorited)
      Glide.with(requireContext())
        .load(composeBackdropImg(it.backdropPath))
        .into(iv_movie_detail_backdrop)
    })
  }

  private fun setupFavButton(favorited: Int) {
    if (favorited == 1) {
      ib_favorite.setImageResource(R.drawable.ic_favorite)
    } else {
      ib_favorite.setImageResource(R.drawable.ic_favorite_border)
    }
    ib_favorite.setOnClickListener {
      if (favorited == 1) {
        viewModel.removeFromFav()
      } else {
        viewModel.addToFav()
      }
    }
  }
}