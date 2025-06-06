package com.example.movieexplorer.movie.domain.repository

import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchDiscoverMovie():Flow<Response<List<Movie>>>
    fun fetchTrendingMovie():Flow<Response<List<Movie>>>
}