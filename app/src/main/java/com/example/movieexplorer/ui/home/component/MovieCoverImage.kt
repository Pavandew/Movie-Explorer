package com.example.movieexplorer.ui.home.component

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieexplorer.movie.domain.models.Movie
import com.example.movieexplorer.ui.home.itemSpacing
import com.example.movieexplorer.utils.Urls

@Composable
fun MovieCoverImage(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (id: Int) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${Urls.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()

    // This Box is for Image
    Box(
        modifier = modifier
            .size(width = 150.dp, height = 225.dp)
            .padding(itemSpacing)
            .clickable { onMovieClick(movie.id) }
    ) {
        AsyncImage(
            model = imgRequest,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)  // clip for rounded corner
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.Crop
        )
        // This movieCard is for BookMark icon
        MovieCard(
            shapes = CircleShape,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Bookmark",
                modifier = Modifier.padding(4.dp)
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Blue.copy(.8f),
            contentColor = Color.White,
            shape = RoundedCornerShape(
                bottomEnd = 30.dp,
                bottomStart = 30.dp
            )
        ) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()) {
                // Movie Title
                Text(
                    text = movie.title,
                    maxLines = 1,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun MovieCoverImagePreview() {
}