package com.example.movieexplorer.movie_detail.data.repo_impl

import com.example.movieexplorer.common.data.ApiMapper
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie.domain.repository.MovieRepository
import com.example.movieexplorer.movie_detail.data.remote.api.MovieDetailApiService
import com.example.movieexplorer.movie_detail.data.remote.models.MovieDetailDTO
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.movie_detail.domain.repository.MovieDetailRepository
import com.example.movieexplorer.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

// Provides the implementation of MovieDetailRepository
class MovieDetailRepositoryImpl (
    private val movieDetailApiService: MovieDetailApiService,
    private val apiDetailMapper: ApiMapper<MovieDetail, MovieDetailDTO>,
    private val apiMovieMapper: ApiMapper<List<Movie>, MovieDto>,
): MovieDetailRepository{

    // Fetches movie details by movie ID and maps the response to the domain model
    override fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>> = flow {
        emit(Response.Loading())
        val movieDetailDto = movieDetailApiService.fetchMovieDetail(movieId )
        apiDetailMapper.mapToDomain(movieDetailDto).apply {
            emit(Response.Success(this))
        }
    }. catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }

    // Fetches a list of movies and maps the response to a list of domain models
    override fun fetchMovie(): Flow<Response<List<Movie>>> = flow {
        emit(Response.Loading())
        val movieDto = movieDetailApiService.fetchMovie()
        apiMovieMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }. catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }

}