package com.example.movieexplorer.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.movie_detail.domain.repository.MovieDetailRepository
import com.example.movieexplorer.utils.Urls
import com.example.movieexplorer.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for handling movie detail logic
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedStateHandle: SavedStateHandle  // This is used to retrieve the movie ID from the saved state
): ViewModel() {
    // MutableStateFlow to hold the state of the movie details
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    // Retrieve the movie ID from the saved state handle, defaulting to -1 if not found
    val id: Int = savedStateHandle.get<Int>(Urls.MOVIE_ID) ?: -1

    init {
        // Fetch movie details when the ViewModel is initialized
        fetchMovieDetailById()
    }

    // Function to fetch movie details by ID
    private fun fetchMovieDetailById() = viewModelScope.launch {
        if(id == -1) { // If the movie ID is -1, update the state with an error message
            _detailState.update {
                it.copy(isLoading = false, error = "Movie not found")
            }
        } else { // If the movie ID is valid, fetch the movie details
            repository.fetchMovieDetail(id).collectAndHandle(
                onError = { error ->
                    _detailState.update {
                        it.copy(isLoading = true, error = error?.message)
                    }
                },
                onLoading = { // Update the state to indicate that movie details are being loaded
                    _detailState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }  // When the movie details are successfully fetched, update the state with the movie details
            ) { movieDetail ->
                _detailState.update {
                    it.copy(isLoading = false,
                        error = null,
                        movieDetail = movieDetail
                    )
                }

            }
        }
    }

    // Function to fetch a list of movies
     fun fetchMovie() = viewModelScope.launch {
        if(id == -1) {
            _detailState.update { // If the movie ID is -1, update the state with an error message
                it.copy(isLoading = false, error = "Movie not found")
            }
        } else { // If the movie ID is valid, fetch the list of movies
            repository.fetchMovie().collectAndHandle(
                onError = { error ->
                    _detailState.update {
                        it.copy(isMovieLoading = true, error = error?.message)
                    }
                },
                onLoading = { // Update the state to indicate that movies are being loaded
                    _detailState.update {
                        it.copy(isMovieLoading = true, error = null)
                    }
                }
            ) { movies ->  // When the movies are successfully fetched, update the state with the list of movies
                _detailState.update {
                    it.copy(
                        isMovieLoading = false,
                        error = null,
                        movies = movies
                    )
                }

            }
        }
    }
}
// Data class representing the state of the DetailViewModel
data class DetailState(
    val movieDetail: MovieDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading : Boolean = false,
    val error: String? = null,
    val isMovieLoading: Boolean = false
)