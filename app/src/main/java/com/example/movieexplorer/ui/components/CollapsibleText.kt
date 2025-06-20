package com.example.movieexplorer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun CollapsibleText(
    modifier: Modifier = Modifier,
    text: String,
    collapsedMaxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    // This is a simple text that can be expanded or collapsed
    val annotatedString = buildAnnotatedString {
        if(!isExpanded) {
            withStyle(SpanStyle(color = Color.Green)) {
                append("see more")
            }

        } else {
            withStyle(SpanStyle(color = Color.Red)) {
                append("see less")
            }
        }
    }
    Column {
        Text(
            text = text,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded },
            maxLines = if(isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = style
        )

        Text(
            text = annotatedString,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded },
            overflow = TextOverflow.Ellipsis,
            style = style
        )
    }
    
}