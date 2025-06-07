package com.example.movieexplorer.movie_detail.data.remote.api

import com.example.movieexplorer.BuildConfig
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.movie_detail.data.remote.models.MovieDetailDTO
import com.example.movieexplorer.utils.Urls
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MOVIE_ID = "movie_id"  // Placeholder for the movie ID in the endpoint
interface MovieDetailApiService {

    @GET("${Urls.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")   // retrofit will replace {$MOVIE_ID} with the actual movie ID when making the request

    suspend fun fetchMovieDetail(
        @Path(MOVIE_ID) movieId: Int, // The movie ID to fetch details for, passed as a path parameter
        @Query("api_key") apiKey: String = BuildConfig.apikey,
        @Query("append_to_response") appendToResponse: String = "credits,reviews"   //videos,credits,reviews,images,similar,watch/providers
    ): MovieDetailDTO

    @GET(Urls.MOVIE_ENDPOINT)
    suspend fun fetchMovie(
        @Query("api_key") apiKey: String = BuildConfig.apikey,
        @Query("include_adult") includeAdult: Boolean = false,
    ) : MovieDto
}