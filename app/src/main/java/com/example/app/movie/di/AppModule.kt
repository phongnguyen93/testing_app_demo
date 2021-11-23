package com.example.app.movie.di

import android.content.Context
import androidx.room.Room
import com.example.app.movie.data.source.DefaultMovieRepository
import com.example.app.movie.data.source.MovieRepository
import com.example.app.movie.data.source.local.LocalMovieDataSource
import com.example.app.movie.data.source.local.MovieDatabase
import com.example.app.movie.data.source.remote.MovieService
import com.example.app.movie.data.source.remote.RemoteMovieDataSource
import com.example.app.movie.util.ServiceConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

  @Singleton
  @Provides
  fun provideMovieService(): MovieService {
    val builder = Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())

    builder.baseUrl(ServiceConst.BASE_URL)
    val httpClient = OkHttpClient.Builder()
    val loggingInterceptor =
      HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    httpClient.addInterceptor(loggingInterceptor)
    return builder.client(httpClient.build()).build()
      .create(MovieService::class.java)
  }

  @Singleton
  @Provides
  fun provideMovieRepository(
    remoteMovieDataSource: RemoteMovieDataSource,
    localMovieDataSource: LocalMovieDataSource
  ): MovieRepository {
    return DefaultMovieRepository(localMovieDataSource, remoteMovieDataSource)
  }

  @Singleton
  @Provides
  fun provideMovieLocalDataSource(
    database: MovieDatabase
  ): LocalMovieDataSource {
    return LocalMovieDataSource(database.movieDao())
  }

  @Singleton
  @Provides
  fun provideMovieRemoteDataSource(
    movieService: MovieService
  ): RemoteMovieDataSource {
    return RemoteMovieDataSource(movieService)
  }

  @Singleton
  @Provides
  fun provideMovieDatabase(
    @ApplicationContext context: Context
  ): MovieDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      MovieDatabase::class.java, "Movies.db"
    ).build()
  }

}