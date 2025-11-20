package com.example.notex.presentation.ui.screen.add

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notex.RoomDb.CodeBlock
import com.example.notex.RoomDb.Note
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.ui.colors.ColorServices
import com.example.notex.ui.components.CodeBlockWidget
import com.example.notex.ui.components.ColorDropdownScreen
import com.example.notex.ui.components.dialogs.ConfirmAlertDialog
import com.example.notex.RoomDb.viewmodel.NoteViewModel

@Composable
fun AddCodeNoteScreen(
    onSave: (Note) -> Unit, // Callback to handle saving the note
    navController: NavController,
    viewModel: NoteViewModel
) {
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }

    val codeList= remember { mutableStateListOf<CodeBlock>() }
    val title= remember { mutableStateOf("title") }
    val showSaveDialog= remember { mutableStateOf(false) }

    Box(){
        Column(
            modifier = Modifier
                .background(selectedColor.value.color)
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
                    .background(Color.Black.copy(0.1f))
            ) {
                IconButton(
                    onClick = {
                        showSaveDialog.value = true

                    },
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                        .size(50.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                IconButton(
                    onClick = {
                        navController.popBackStack()
                        //TODO insert code note

                        viewModel.addCodeNote(
                            title = title.value,
                            content = "",
                            color = selectedColor.value.color.toArgb(),

                            codeBlocks = codeList
                        )
                        showSaveDialog.value = false
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                        .size(50.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "ADD", tint = Color.White)
                }
            }
            OutlinedTextField(

                modifier = Modifier.padding(start = 4.dp).fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.8f)
                    , unfocusedContainerColor = Color.White.copy(alpha = 0.5f)
                   , focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.DarkGray
                )
                ,
                value = title.value,
                onValueChange = {
                    title.value = it
                }
            )
            Card  (
                shape = RectangleShape,
                modifier = Modifier.padding(4.dp).fillMaxWidth()
                , colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.5f)
                )

            ){
                ColorDropdownScreen(
                    selectedColor = selectedColor
                )
            }
            if (showSaveDialog.value) {
                var context = LocalContext.current
                ConfirmAlertDialog(
                    onConfirm = {


                        //TODO insert code note

                        viewModel.addCodeNote(
                            title = title.value,
                            content = "",
                            color = selectedColor.value.color.toArgb(),

                            codeBlocks = codeList
                        )
                        navController.popBackStack()

                        showSaveDialog.value = false
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

                LazyColumn(

                    modifier = Modifier.padding(bottom = 150.dp)

                ) {
                    itemsIndexed(codeList)
                    { i, codeBlok ->

                        var description = remember { mutableStateOf("Description") }
                        var language = remember { mutableStateOf("Java") }
                        var code = remember { mutableStateOf("Java()") }
                        codeList.set(
                            index = i, element = CodeBlock(
                                description = description.value,
                                code = code.value,
                                language = language.value
                            )
                        )
                        CodeBlockWidget(
                            editable = true,
                            description = description,
                            language = language,

                            code = code,
                        )


                    }
                }



        }
        FloatingActionButton(
            containerColor = ColorServices().darkBlue,
            onClick = {
                println(codeList)
                codeList.add(

                    CodeBlock(
                        description = "TODO()",
                        code = "TODO()",
                        language = "TODO()"
                    )
                )
            },
            modifier = Modifier

                .align(Alignment.BottomEnd)
                .padding(16.dp).padding(bottom = 50.dp)

        ) {
            Icon(
                tint = Color.White,

            imageVector =     Icons.Default.Add, contentDescription = "Add Code Block")
        }
    }
}


