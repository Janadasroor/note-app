package com.example.notex.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectableNoteText(content: String) {
    // Create an AnnotatedString that highlights the links in the content
    val annotatedString = buildAnnotatedString {
        val regex = "(https?://[\\w-]+(?:\\.[\\w-]+)+(/[^\\s]*)?)".toRegex() // Regex to match URLs

        // Loop through the content to find URLs and apply the custom style
        var lastIndex = 0
        regex.findAll(content).forEach { matchResult ->
            // Append text before the link
            append(content.substring(lastIndex, matchResult.range.first))

            // Apply style for the URL
            withStyle(style = SpanStyle(color = Color.Blue)) {
                pushStringAnnotation(tag = "URL", annotation = matchResult.value)
                append(matchResult.value)
                pop()
            }
            // Update last index for the next loop
            lastIndex = matchResult.range.last + 1
        }

        // Append the remaining text after the last URL
        append(content.substring(lastIndex))
    }

    // Use ClickableText to handle clicks on URLs
    SelectionContainer {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                // Check if a URL is clicked
                annotatedString.getStringAnnotations("URL", offset, offset)
                    .firstOrNull()?.let {
                        // Open the URL in a browser
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.item))
                        // If you are in an activity, you can start the intent like this:
                        //context.startActivity(intent)
                    }
            },
            style = TextStyle(fontSize = 18.sp, color = Color.DarkGray, lineHeight = 28.sp),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(5.dp)
        )
    }
}