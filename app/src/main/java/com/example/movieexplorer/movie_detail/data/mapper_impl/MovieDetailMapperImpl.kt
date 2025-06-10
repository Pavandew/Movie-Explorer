package com.example.movieexplorer.movie_detail.data.mapper_impl

import com.example.movieexplorer.common.data.ApiMapper
import com.example.movieexplorer.movie_detail.data.remote.models.CastDto
import com.example.movieexplorer.movie_detail.data.remote.models.MovieDetailDTO
import com.example.movieexplorer.movie_detail.domain.models.Cast
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.movie_detail.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Locale

class MovieDetailMapperImpl: ApiMapper<MovieDetail, MovieDetailDTO> {

    // This mapper converts the API DTO to the domain model for MovieDetail
    override fun mapToDomain(apiDto: MovieDetailDTO): MovieDetail {
        return MovieDetail(
            backdropPath = formatEmptyValue(apiDto.backdropPath),
            genresId = apiDto.genres?.map { formatEmptyValue(it?.name) } ?: emptyList(),
            id = apiDto.id ?: 0,
            originalLanguage = formatEmptyValue(apiDto.originalLanguage, "language"),
            originalTitle = formatEmptyValue(apiDto.originalTitle, "title"),
            overview = formatEmptyValue(apiDto.overview, "overview"),
            popularity = apiDto.popularity ?: 0.0,
            posterPath = formatEmptyValue(apiDto.posterPath),
            releaseDate = formatEmptyValue(apiDto.releaseDate, "date"),
            title = formatEmptyValue(apiDto.title, "title"),
            voteAverage = apiDto.voteAverage ?: 0.0,
            voteCount = apiDto.voteCount ?: 0,
            video = apiDto.video ?: false,
            cast = formatCast(apiDto.credits?.cast),
            language = apiDto.spokenLanguages?.map { formatEmptyValue(it?.englishName) } ?: emptyList(),
            productionCountries = apiDto.productionCountries?.map { formatEmptyValue(it?.name)} ?: emptyList(),
            reviews = apiDto.reviews?.results?.map {
                Review (
                    author = formatEmptyValue(it?.author),
                    content = formatEmptyValue(it?.content),
                    createdAt = formatTimeStamp(time = it?.createdAt ?: ""),
                    id = formatEmptyValue(it?.id),
                     rating = it?.authorDetails?.rating ?: 0.0
                )
            } ?: emptyList(),
            runtime = convertMinutesToHours(apiDto.runtime ?: 0)
        )
    }
    private fun formatTimeStamp(pattern: String = "dd.MM.yy", time: String): String {
        return try {
            val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
            val outputDataFormatter = SimpleDateFormat(pattern, Locale.getDefault())
            val date = inputDateFormatter.parse(time)
            date?.let { outputDataFormatter.format(it) } ?: time
        } catch (e: Exception) {
            time
        }
    }
//    private fun formatTimeStamp(pattern: String = "dd.MM.YY", time: String) : String {
//        val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd't'HH:mm:ss.SSS'Z'", Locale.getDefault())
//
//        val outputDataFormatter = SimpleDateFormat(
//            pattern,
//            Locale.getDefault()
//        )
//
//        // Parse the input date String
//        val date = inputDateFormatter.parse(time)
//
//        // Format the parsed date to the desired pattern
//        val formattedDate = date?.let { outputDataFormatter.format(it) } ?: time
//
//        return formattedDate
//    }

    // This is for converting movie length in minutes to hours and minutes format
    private fun convertMinutesToHours(minutes: Int): String {
        val hours = minutes/60
        val remainingMinutes = minutes % 60
        return "${hours}h:${remainingMinutes}m"
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if(value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatCast(castDto: List<CastDto?>?): List<Cast> {
        return castDto?.map {
            val genderRole = if(it?.gender == 2) "Actor" else "Actress"
            Cast (
                id = it?.id ?: 0,
                name = formatEmptyValue(it?.name),
                genderRole = genderRole,
                character = formatEmptyValue(it?.character),
                profilePath = it?.profilePath ?: "",
            )
        } ?: emptyList()
    }
}