package com.example.movieexplorer.movie_detail.domain.models


data class MovieDetail(
    val backdropPath: String? = null,
//    val budget: Int? = null,
//    val credits: Credits? = null,
    val genresId: List<String?>? = null,
//    val homepage: String? = null,
    val id: Int? = null,
//    val imdbId: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
//    val productionCompanies: List<ProductionCompany?>? = null,
//    val productionCountries: List<ProductionCountry?>? = null,
    val releaseDate: String? = null,
//    val revenue: Int? = null,
    val reviews: List<Review>,
    val runtime: String? = null,
//    val spokenLanguages: List<SpokenLanguage?>? = null,
//    val status: String? = null,
//    val tagline: String? = null,
    val title: String? = null ,
    val voteAverage: Double? = null ,
    val voteCount: Int? = null ,
    val video: Boolean? = null ,
    val cast: List<Cast>,
    val language: List<String>,
    val productionCountries: List<String?>? = null,
)
data class Review (
    val author: String? = null,
    val content: String? = null,
    val createdAt: String? = null,
    val id: String? = null,
    val rating: Double = 0.0
)

data class Cast (
    val id: Int,
    val name:String,
    val genderRole: String,
    val character: String,
    val profilePath: String?,
) {
    private val nameParks = name.split(" ", limit = 2)
    val firstName = nameParks[0]
    val lastName = if(name.length > 1) nameParks[1] else ""
}