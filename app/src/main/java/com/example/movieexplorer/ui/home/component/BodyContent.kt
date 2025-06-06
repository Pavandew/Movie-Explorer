package com.example.movieexplorer.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.ui.home.itemSpacing

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    discoverMovies: List<Movie>,
    trendingMovies: List<Movie>,
    onMovieClick:(id: Int) -> Unit
) {
    LazyColumn (modifier = modifier){
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                // For Header Title like "Discover Movies"
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Discover Movies",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick =  { /*TODO */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "More Discover Movies"
                        )
                    }
                }
                // For Scroll Horizontal List of Discover Movies
                   LazyRow {
                       items(discoverMovies) {
                           MovieCoverImage(
                               movie = it,
                               onMovieClick = onMovieClick)
                       }
                   }
                // For Header Title "Trending Movies"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Trending Movies",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "More Trending Movies"
                        )
                    }
                }
                // For Scroll Horizontal List of Trending Movies
                LazyRow {
                    items(trendingMovies) {
                        MovieCoverImage(
                            movie = it,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}