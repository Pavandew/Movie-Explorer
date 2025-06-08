package com.example.movieexplorer.ui.detail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.movie_detail.domain.models.MovieDetail
import com.example.movieexplorer.movie_detail.domain.models.Review
import com.example.movieexplorer.ui.home.component.MovieCard
import com.example.movieexplorer.ui.home.component.MovieCoverImage
import com.example.movieexplorer.ui.home.defaultPadding
import com.example.movieexplorer.ui.home.itemSpacing

@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movies: List<Movie>,
    isMovieLoading:Boolean,
    fetchMovies:() -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
) {
    LazyColumn (modifier){
        item {
            Card (
                modifier = Modifier.fillMaxWidth()
            ){
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)
                ){
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        // Display movie title (Science Fiction, Action, etc.)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            movieDetail.genresId?.forEachIndexed { index, genreText ->
                                Text(
                                    text = genreText ?: "N/A",
                                    modifier = Modifier
                                        .padding(6.dp),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                // show divider after all except the last item
                                if(index != movieDetail.genresId?.lastIndex) {
                                    Text(
                                        text = " \u2022",   // Unicode for bullet(.) point
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        // Display movie Runtime (e.g., 2h 30m)
                        Text(
                            text = movieDetail.runtime ?: "N/A",
                            style = MaterialTheme.typography.bodySmall,
                        )

                    }

                    Spacer(modifier = Modifier.height(itemSpacing))
                    // Display movie Name (e.g., "Inception")
                    Text(
                        text = movieDetail.title ?: "N/A",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    // Display movie Overview (e.g., "A mind-bending thriller...")
                    Text(
                        text = movieDetail.overview ?: "No overview available.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    // display action Buttons (e.g., "BookMark", "share", "download")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        ActionIcon.entries.forEachIndexed { index, actionIcon ->
                            ActionIconBtn(
                                icon = actionIcon.icon,
                                contentDescription = actionIcon.contentDescription,
                                bgColor = if(index == ActionIcon.entries.lastIndex) // Use primaryContainer color for the last icon
                                MaterialTheme.colorScheme.primaryContainer
                                else Color.Black.copy(
                                    .5f
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(itemSpacing))
                    // Cast & Crew Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = itemSpacing),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cast & Crew",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Cast & Crew",
                            )
                        }
                    }

                    // Display Cast & Crew list
                    LazyRow {
                        items(movieDetail.cast) { castMember ->
                            ActorItem(
                                cast = castMember,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onActorClick(castMember.id) }
                            )
                            Spacer(modifier = Modifier.width(defaultPadding))
                        }
                    }

                    Spacer(modifier = Modifier.height(itemSpacing))

                    MovieInfoItem(infoItem = movieDetail.language, title = "Spoken Languages")
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MovieInfoItem(
                        infoItem = movieDetail.productionCountries,
                        title = "Production countries"
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text (
                        text = "Reviews",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = movieDetail.reviews)
                    Spacer(modifier = Modifier.height(itemSpacing))

                    MoreLikeThis(
                        fetchMovies = fetchMovies,
                        isMovieLoading = isMovieLoading,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
fun MoreLikeThis(
    modifier: Modifier = Modifier,
    fetchMovies: () -> Unit,
    isMovieLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    // LaunchedEffect because we need to fetch movies when this composable is first composed
    LaunchedEffect(key1 = true) {
        fetchMovies()
    }
    Column(modifier) {
        Text(
            text = "More Like This",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        LazyRow {
            item {
                AnimatedVisibility(visible = isMovieLoading) {
                    CircularProgressIndicator()
                }
            }
            items(movies) {
                MovieCoverImage(movie = it, onMovieClick = onMovieClick)
            }
        }
    }
}
// Enum class because we need to use it in a loop
private enum class ActionIcon(val icon: ImageVector, val contentDescription: String) {
    BookMark(icon = Icons.Default.Bookmark, "Bookmark"),
    Share(icon = Icons.Default.Share, "Share"),
    Download(icon = Icons.Default.Download, "Download"),
}
// Composable function to display action buttons (Bookmark, Share, Download)
@Composable
private fun ActionIconBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    bgColor: Color = Color.Black.copy(.8f)
) {
    MovieCard (
        shapes = CircleShape,
        modifier = modifier
            .padding(4.dp),
        bgColor = bgColor
    ){
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(4.dp)
        )
    }
}

// InfoItem use because we need to display multiple items in a row
@Composable
private fun MovieInfoItem(infoItem: List<String?>?, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text (
            text = title,
            style =  MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(4.dp))
        infoItem?.forEach {
            Text(
                text = it?: "N/A",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Composable function to display reviews
@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review>
) {
    val (viewMore, setViewMore) =  remember {
        mutableStateOf(false)
    }
    // show only three reviews or less by default
    val defaultReview =
        if(reviews.size > 3) reviews.take(3) else reviews

    //show more when user clicks on "View More"
    val movieReviews = if (viewMore) reviews else defaultReview
    // Button text changes based on the viewMore state
    val btnText = if(viewMore) "Collapse" else "More..."
    Column {
        // Display each review item
        movieReviews.forEach { review ->
            ReviewItem(review = review, modifier = modifier)
            Spacer(modifier = Modifier.height(itemSpacing))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(itemSpacing))
        }
        TextButton(onClick = { setViewMore(!viewMore)}) {
            Text(text = btnText)
        }
    }
    
}