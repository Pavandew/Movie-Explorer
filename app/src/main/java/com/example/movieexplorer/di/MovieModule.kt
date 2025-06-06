package com.example.movieexplorer.di

import com.example.movieexplorer.common.data.ApiMapper
import com.example.movieexplorer.movie.data.mapper_impl.MovieApiMapperImpl
import com.example.movieexplorer.movie.data.remote.api.MovieApiService
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.movie.data.repository_impl.MovieRepositoryImpl
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie.domain.repository.MovieRepository
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
object MovieModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        mapper:ApiMapper<List<Movie>, MovieDto>
    ):MovieRepository = MovieRepositoryImpl (
        movieApiService, mapper
    )

    @Provides
    @Singleton
    fun provideMapper():ApiMapper<List<Movie>, MovieDto> = MovieApiMapperImpl()

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService{
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }

}
