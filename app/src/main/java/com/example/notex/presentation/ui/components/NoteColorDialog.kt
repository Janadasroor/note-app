package com.example.notex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notex.RoomDb.models.ColorObject

@Composable
fun NoteColorDialog(
    selectedColor: MutableState<ColorObject>,
    onDismiss: () -> Unit
) {
    // List of colors for notes
    val noteColors = listOf(
        ColorObject(Color(0xFF002F50), "Deep Navy"),
        ColorObject(Color(0xFF003D32), "Forest Green"),
        ColorObject(Color(0xFF4A4A4A), "Charcoal Gray"),
        ColorObject(Color(0xFF4B0032), "Burgundy Red"),
        ColorObject(Color(0xFFD78815), "Golden Amber"),
        ColorObject(Color(0xFF8C3417), "Rusty Red"),
        ColorObject(Color(0xFF560622), "Crimson Red"),
        ColorObject(Color(0xFF2196F3), "Blue"),
        ColorObject(Color(0xFF4CAF50), "Green"),
        ColorObject(Color(0xFFF44336), "Red"),
        ColorObject(Color(0xFFFF9800), "Orange"),
        ColorObject(Color(0xFF9C27B0), "Purple"),
        ColorObject(Color(0xFFFFEB3B), "Yellow"),
        ColorObject(Color(0xFF795548), "Brown"),
        ColorObject(Color(0xFF607D8B), "Blue Gray"),
        ColorObject(Color(0xFF009688), "Teal")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Select Note Color",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column {
                Text(
                    text = "Choose a color for your note:",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(noteColors) { color ->
                        val isSelected = selectedColor.value.color == color.color
                        
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(color.color)
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedColor.value = color
                                }
                        )
                    }
                }
                
                // Selected color display
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(selectedColor.value.color)
                    )
                    Text(
                        text = selectedColor.value.name,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
} 