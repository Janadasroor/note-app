package com.example.notex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.notex.RoomDb.models.ColorObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDropdownScreen(selectedColor: MutableState<ColorObject>) {
    // List of deep colors
    val deepColors = listOf(
        ColorObject(Color(0xFF002F50), "Deep Navy"),
        ColorObject(Color(0xFF003D32), "Forest Green"),
        ColorObject(Color(0xFF4A4A4A), "Charcoal Gray"),
        ColorObject(Color(0xFF4B0032), "Burgundy Red"),
        ColorObject(Color(0xFFD78815), "Golden Amber"),
        ColorObject(Color(0xFF8C3417), "Rusty Red"),
        ColorObject(Color(0xFF560622), "Crimson Red")
    )

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray,
                    focusedLabelColor = Color.DarkGray
                ),
                value = selectedColor.value.name,
                onValueChange = { },
                label = { Text("Select Option", ) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                },
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                leadingIcon = { // Add colored box inside the TextField
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(selectedColor.value.color)
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                deepColors.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(

                                    text = option.name,
                                    modifier = Modifier.weight(1f) // Push color box to the right
                                )
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(option.color)
                                        .padding(start = 8.dp) // Add spacing between text and box
                                )
                            }
                        },
                        onClick = {
                            selectedColor.value = option
                            println(selectedColor.value.color.toArgb())
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
