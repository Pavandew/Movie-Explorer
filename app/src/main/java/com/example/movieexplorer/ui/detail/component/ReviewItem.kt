package com.example.movieexplorer.ui.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.movieexplorer.movie_detail.domain.models.Review
import com.example.movieexplorer.ui.components.CollapsibleText
import com.example.movieexplorer.ui.home.itemSpacing
import kotlin.math.round

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
) {
    Column (modifier){
        // variable to the review author name and created date
        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" - ")
            append(review.createdAt)
        }

        // variable to the review content
        val ratingAnnotatedString = buildAnnotatedString {
            // Apply bold style to "6/"
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString())  // round to the nearest integer for display
            pop() // End bold styling

            // Apply small font size to "/10" part
            pushStyle(SpanStyle(fontSize = 10.sp))
            append("/10")
            pop() // End small font size styling
        }

        // Display the review content with rating
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        // Display the review
        CollapsibleText(text = review.content ?: "null", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(itemSpacing))
         // Display the rating
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }
}