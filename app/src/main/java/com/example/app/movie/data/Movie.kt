package com.example.app.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieListResponse(
  @SerializedName("page") var page: Int,
  @SerializedName("results") var results: List<Movie>,
  @SerializedName("total_pages") var totalPages: Int,
  @SerializedName("total_results") var totalResults: Int
)

@Entity(tableName = "movies")
data class Movie(
  @Ignore @SerializedName("adult") var adult: Boolean = false,
  @Ignore @SerializedName("backdrop_path") var backdropPath: String = "",
  @Ignore @SerializedName("genre_ids") var genreIds: List<Int> = emptyList(),
  @PrimaryKey @ColumnInfo(name = "entryid") @SerializedName("id") var id: Int = -1,
  @Ignore @SerializedName("original_language") var originalLanguage: String = "",
  @Ignore @SerializedName("original_title") var originalTitle: String = "",
  @ColumnInfo(name = "overview") @SerializedName("overview") var overview: String = "",
  @Ignore @SerializedName("popularity") var popularity: Double = 0.0,
  @Ignore @SerializedName("poster_path") var posterPath: String = "",
  @Ignore @SerializedName("release_date") var releaseDate: String = "",
  @ColumnInfo(name = "title") @SerializedName("title") var title: String = "",
  @Ignore @SerializedName("video") var video: Boolean = false,
  @Ignore @SerializedName("vote_average") var voteAverage: Double = 0.0,
  @Ignore @SerializedName("vote_count") var voteCount: Int = 0
)