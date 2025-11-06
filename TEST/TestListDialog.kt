package com.example.myapplication.TEST


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.RoomDb.NoteType
@Preview
@Composable
fun OptionsDialog(
//    onDismiss: () -> Unit,
//    onOptionSelected: (NoteType) -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Card(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Select Note Type",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // List of note types
                NoteType.entries.forEach { noteType ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2A5298)
                        )
                        ,
                        onClick = {
//                            onOptionSelected(noteType)
//                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = noteType.name.replace("_", " "))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
