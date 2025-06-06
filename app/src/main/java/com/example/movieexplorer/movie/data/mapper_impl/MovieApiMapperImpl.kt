package com.example.movieexplorer.movie.data.mapper_impl

import androidx.compose.ui.window.isPopupLayout
import com.example.movieexplorer.common.data.ApiMapper
import com.example.movieexplorer.movie.data.remote.models.MovieDto
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.utils.GenericConstants

class MovieApiMapperImpl : ApiMapper<List<Movie>, MovieDto> {

    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.results?.map { result ->
            Movie (
                backdropPath = formatEmptyValue(result?.backdropPath),
                genreIds = formatGenre(result?.genreIds),
                id = result?.id ?: 0,
                originalLanguage = formatEmptyValue(result?.originalLanguage),
                originalTitle = formatEmptyValue(result?.originalTitle, "title"),
                overView = formatEmptyValue(result?.overview, "overView"),
                popularity = result?.popularity ?: 0.0,
                posterPath = formatEmptyValue(result?.posterPath),
                releaseDate = formatEmptyValue(result?.releaseDate, "date"),
                title = formatEmptyValue(result?.title, "title"),
                voteAverage = result?.voteAverage ?: 0.0,
                voteCount = result?.voteCount ?: 0,
                video = result?.video ?: false

            )
        } ?: emptyList()
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if(value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatGenre(genreIds: List<Int?>?): List<String> {
        return genreIds?.map { GenericConstants.getGenreNameById(it ?: 0)} ?: emptyList()
    }

}