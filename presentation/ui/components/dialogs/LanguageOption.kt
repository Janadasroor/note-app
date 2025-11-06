package com.example.myapplication.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.colors.ColorServices

@Composable
fun LanguageOptionsDialog(
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val languages = listOf("Python", "C", "C++", "Java", "Kotlin", "JavaScript")

    AlertDialog(

        shape = CircleShape.copy(CornerSize(4)),
        onDismissRequest = onDismiss,
        title = { Text("Select a Language") },
        text = {
            Column (

            ){
                languages.forEachIndexed { index, language ->
                    Card(
                        modifier = Modifier.
                        fillMaxWidth()
                            .padding(3.dp)
                            , shape = RectangleShape
                    ){
                        Text(
                            text = language,
                            modifier = Modifier

                                .padding(3.dp)
                                .clickable { onSelect(language) },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }


                }
            }
        },
        confirmButton = {
            TextButton(
                shape = CircleShape.copy(CornerSize(4.dp)),
                colors = ButtonDefaults.buttonColors(

                    containerColor = ColorServices().darkBlue
                )
                ,
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}


@Preview
@Composable
fun PreviewLanguageOptionsDialog() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        LanguageOptionsDialog(
            onDismiss = { showDialog = false },
            onSelect = { selectedLanguage ->
                println("Selected: $selectedLanguage")
                showDialog = false
            }
        )
    }
}
