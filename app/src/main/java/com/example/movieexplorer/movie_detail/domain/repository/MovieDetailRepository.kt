package com.example.movieexplorer.movie_detail.domain.repository

import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId:Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}