/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.app.movie.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.movie.data.Movie
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data Access Object for the movies table.
 */
@Dao
interface MovieDao {

  /**
   * Observes list of favorite movies.
   *
   * @return all favorited movies.
   */
  @Query("SELECT * FROM Movies")
  fun observeMovies(): Flowable<List<Movie>>

  /**
   * Observes a single movie.
   *
   * @param movieId the movie id.
   * @return the movie with movieId.
   */
  @Query("SELECT * FROM Movies WHERE entryid = :movieId")
  fun observeMovieById(movieId: Int): Flowable<Movie>

  /**
   * Insert a movie in the database. If the movie already exists, replace it.
   *
   * @param movie the movie to be inserted.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMovie(movie: Movie): Completable


  /**
   * Delete a movie by id.
   *
   * @return the number of movies deleted. This should always be 1.
   */
  @Query("DELETE FROM Movies WHERE entryid = :movieId")
  fun deleteMovieById(movieId: Int)
}
