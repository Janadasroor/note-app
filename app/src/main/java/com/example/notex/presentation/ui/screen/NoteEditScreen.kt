package com.example.notex.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.ui.components.ColorDropdownScreen
import com.example.notex.RoomDb.viewmodel.NoteViewModel

@Composable
fun NoteEditScreen(navController: NavController, viewModel: NoteViewModel) {
    val note = viewModel.selectedNote ?: return
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
        val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(selectedColor.value.color, Color(0xFF1E1E1E))
                )
            )
            .padding(16.dp)
    ) {
       Row(
           horizontalArrangement = Arrangement.SpaceBetween,
           modifier = Modifier
               .padding(top = 50.dp)
               .fillMaxWidth()
       ) {   // 🔙 Back Button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
           IconButton(
               onClick = {
                   note.type?.let {
                       viewModel.updateNote(
                           id = note.id,
                           title = title,
                           content = content,
                           color = selectedColor.value.color.toArgb(),
                           type = it
                       )
                   }
                   navController.popBackStack()
               },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
           // 💾 Save Button

        }
        Spacer(modifier = Modifier.height(16.dp))

        // 📝 Editable Title
        TextField(
            value = title,
            onValueChange = { title = it },
            textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
 Box(
     modifier = Modifier
         .fillMaxHeight().clip(RoundedCornerShape(6.dp))

         .background(color = Color.White )


 ){
     Column (

     ){//region white
         Spacer(modifier = Modifier.height(8.dp))
         // 🎨 Color Picker
         ColorDropdownScreen(
             selectedColor = selectedColor
         )

         Spacer(modifier = Modifier.height(20.dp))

         // ✍ Editable Content
         TextField(
             value = content,
             onValueChange = { content = it },
             textStyle = MaterialTheme.typography.bodyLarge,
             colors = TextFieldDefaults.colors(
                 focusedContainerColor = Color.Transparent,
                 unfocusedContainerColor = Color.Transparent,
                 cursorColor = Color.Black,
                 focusedIndicatorColor = Color.Gray
             ),
             modifier = Modifier
                 .fillMaxSize()
                 .verticalScroll(rememberScrollState())
         )


     }
 }
//endregion
    }
}


@Composable
fun ColorPicker(selectedColor: Int, onColorSelected: (Int) -> Unit) {
    val colors = listOf(0xFFA726, 0x66BB6A, 0x29B6F6, 0xAB47BC, 0xFF7043)

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(color), shape = CircleShape)
                    .clickable { onColorSelected(color) }
            )
        }
    }
}
