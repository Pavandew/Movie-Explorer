package com.example.movieexplorer.movie.data.remote.api

import com.example.movieexplorer.BuildConfig
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.utils.Urls
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(Urls.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.apikey,
        @Query("include_adult")  includeAdult: Boolean = false
    ): MovieDto

    @GET(Urls.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.apikey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto


}