package com.example.movieexplorer.di

import com.example.movieexplorer.common.data.ApiMapper
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie_detail.data.mapper_impl.MovieDetailMapperImpl
import com.example.movieexplorer.movie_detail.data.remote.api.MovieDetailApiService
import com.example.movieexplorer.movie_detail.data.remote.models.MovieDetailDTO
import com.example.movieexplorer.movie_detail.data.repo_impl.MovieDetailRepositoryImpl
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.movie_detail.domain.repository.MovieDetailRepository
import com.example.movieexplorer.utils.Urls
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    // Provides a JSON instance with specific configurations for serialization
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    // Provides the MovieDetailRepository implementation for dependency injection
    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        mapper: ApiMapper<MovieDetail, MovieDetailDTO>,
        movieMapper: ApiMapper<List<Movie>, MovieDto>
        ) : MovieDetailRepository = MovieDetailRepositoryImpl(
        movieDetailApiService = movieDetailApiService,
            apiDetailMapper = mapper,
            apiMovieMapper = movieMapper,
    )

    // Provides the API mapper for converting MovieDetailDTO to MovieDetail
    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<MovieDetail, MovieDetailDTO> = MovieDetailMapperImpl()

    // Provides the API mapper for converting MovieDto to List<Movie>
    @Provides
    @Singleton
    fun provideMovieDetailApiService(): MovieDetailApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieDetailApiService::class.java)
    }
}