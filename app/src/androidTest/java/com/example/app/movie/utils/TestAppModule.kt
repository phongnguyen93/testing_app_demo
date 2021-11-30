package com.example.app.movie.utils

import android.content.Context
import androidx.room.Room
import com.example.app.movie.data.source.DefaultMovieRepository
import com.example.app.movie.data.source.MovieDataSource
import com.example.app.movie.data.source.MovieRepository
import com.example.app.movie.data.source.local.LocalMovieDataSource
import com.example.app.movie.data.source.local.MovieDatabase
import com.example.app.movie.di.AppModule
import com.example.app.movie.di.LocalDataSource
import com.example.app.movie.di.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [AppModule::class]
)
@Module
class TestAppModule {

  @Singleton
  @Provides
  fun provideMovieRepository(
    @RemoteDataSource remoteMovieDataSource: MovieDataSource,
    @LocalDataSource localMovieDataSource: MovieDataSource
  ): MovieRepository {
    return DefaultMovieRepository(localMovieDataSource, remoteMovieDataSource)
  }

  @LocalDataSource
  @Singleton
  @Provides
  fun provideMovieLocalDataSource(
    database: MovieDatabase
  ): MovieDataSource {
    return LocalMovieDataSource(database.movieDao())
  }

  @RemoteDataSource
  @Singleton
  @Provides
  fun provideMovieRemoteDataSource(): MovieDataSource {
    return TestMovieDataSource()
  }

  @Singleton
  @Provides
  fun provideMovieDatabase(
    @ApplicationContext context: Context
  ): MovieDatabase {
    return Room.inMemoryDatabaseBuilder(
      context,
      MovieDatabase::class.java
    ).build()
  }
}