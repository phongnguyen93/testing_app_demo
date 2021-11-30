package com.example.app.movie.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.movie.R
import com.example.app.movie.data.Movie
import com.example.app.movie.util.composeBackdropImg
import kotlinx.android.synthetic.main.item_movie_list.view.*
import java.util.*

interface ItemAction {
  fun onClick(item: Movie)
}

class MovieListAdapter(private val itemAction: ItemAction) :
  RecyclerView.Adapter<MovieListItemVH>() {

  private val dataSource = ArrayList<Movie>()

  @SuppressLint("NotifyDataSetChanged")
  fun setData(data: List<Movie>) {
    dataSource.clear()
    dataSource.addAll(data)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListItemVH {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
    return MovieListItemVH(itemAction, view)
  }

  override fun onBindViewHolder(holder: MovieListItemVH, position: Int) {
    holder.bindItem(dataSource[position])
  }

  override fun getItemCount(): Int {
    return dataSource.size
  }
}

class MovieListItemVH(private val itemAction: ItemAction, itemView: View) :
  RecyclerView.ViewHolder(itemView) {
  fun bindItem(item: Movie) {
    itemView.tv_movie_title.text = item.title
    Glide.with(itemView.context)
      .load(composeBackdropImg(item.backdropPath))
      .into(itemView.iv_movie_backdrop)
    itemView.setOnClickListener {
      itemAction.onClick(item)
    }
  }
}