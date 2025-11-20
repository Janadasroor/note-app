package com.example.notex.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.ui.components.ColorDropdownScreen
import com.example.notex.ui.components.dialogs.ConfirmAlertDialog
import com.example.notex.RoomDb.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)

//@Preview
//@Composable
//fun PreviewNoteAddScreen(){
//val context=LocalContext.current
//    val database = remember { NoteDatabase.getDatabase(context) }
//val app=context.applicationContext as Application
//    val viewModelFactory = ViewModelFactory(database,app)
//
//    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
//
//    //NoteAddScreen(viewModel)
//
//}

@Composable
fun NoteAddScreen(viewModel: NoteViewModel, navController: NavController) {
    val noteTitle = remember { mutableStateOf("Note Title") }
    val noteDescription = remember { mutableStateOf("Note Description") }
    val formattedDate = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault()).format(Date(System.currentTimeMillis()))
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }
    val scrollState= rememberScrollState()
    val clipBoardManager=    LocalClipboardManager.current
    val selectedNoteType=viewModel.selectedNoteType
    var showSaveDialog= remember { mutableStateOf(false) }

   Box(
       modifier = Modifier
           .fillMaxSize()
           .background(
               Brush.verticalGradient(
                   colors = listOf(selectedColor.value.color, Color.LightGray)
               )
           )
           .imePadding()
   ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            selectedColor.value.color,
                            Color.LightGray
                        ) // Gradient background
                    )
                )
                .padding(16.dp, 50.dp, 10.dp, 10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 🔙 Back Button
                IconButton(
                    onClick = {

                      //  navController.navigate("home")
                        showSaveDialog.value=true
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(

                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                // 🔙 Save Button
                IconButton(
                    onClick = {
                        //TODO Add note to the table
                        if (selectedNoteType != null) {
                            viewModel.addNote(
                                title = noteTitle.value,
                                content = noteDescription.value,
                                color = selectedColor.value.color.toArgb(),
                                type = selectedNoteType
                            )
                        }
                        if (selectedNoteType != null) {
                            println("MSG ${selectedNoteType?.name}")
                        }
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }
            }
            val context= LocalContext.current
            if (showSaveDialog.value) {
                ConfirmAlertDialog(
                    onConfirm = {
                        if (selectedNoteType != null) {
                            viewModel.addNote(
                                title = noteTitle.value,
                                content = noteDescription.value,
                                color = selectedColor.value.color.toArgb(),
                                type = selectedNoteType
                            )
                        }

                        showSaveDialog.value = false
                        navController.navigate("home")
                        Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
                    },
                    onDismiss = {
                        showSaveDialog.value = false
                        navController.navigate("home")

                    },
                    msg = "Do you want to save it.",
                    title = "Confirm save"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 📌 Note Title input
            TextField(
                noteTitle.value, { noteTitle.value = it }, Modifier.fillMaxWidth(),
                label = { Text(text = "Title") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🕒 Timestamp
            Text(
                text = formattedDate,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 📜 Note Content (Description)
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    ColorDropdownScreen(
                        selectedColor = selectedColor
                    )
                    // 📄 Description Text Field
                    Box(modifier = Modifier.fillMaxSize()) {
                        TextField(
                            value = noteDescription.value,
                            onValueChange = { noteDescription.value = it },
                            label = { Text(text = "Description") },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 50.dp),  // Space for icon
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                focusedTextColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            maxLines = 200,
                            minLines = 5,
                            placeholder = {
                                Text(text = "Write your note content here...")
                            }
                        )

                        // 📋 Past Icon Button (Aligned to the Top End)
                        IconButton(
                            onClick = {
                         val text =clipBoardManager.getText()?.text?:""
                             if(text.isNotEmpty()){
                                 noteDescription.value=text
                             }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Filled.ContentPaste,
                                contentDescription = "Copy Content",
                                colorFilter = ColorFilter.tint(Color.DarkGray)
                            )
                        }
                    }
                }
            }
        }
    }
}

