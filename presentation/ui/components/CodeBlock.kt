package com.example.myapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.dialogs.LanguageOptionsDialog

@Composable

fun CodeBlockWidget(

    description:MutableState<String>,
    language:MutableState<String>,
    code:MutableState<String>,

    editable: Boolean
){
    var clipManger= LocalClipboardManager.current
    var optionDialog= remember { mutableStateOf(false) }
    Card(
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()

            .padding(bottom = 4.dp, top = 10.dp)

    ) {
       if(!editable) {
           SelectionContainer {
               Text(
                   text = description.value,
                   modifier = Modifier.padding(10.dp)
               )
           }
       }
        else
           OutlinedTextField(
               modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Gray.copy(alpha = 0.2f)
                    , unfocusedContainerColor = Color.Gray.copy(alpha = 0.3f)
                    , focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.DarkGray
                ),
                            value = description.value,
                onValueChange = {
                    description.value=it
                }
            )
    }
    if(optionDialog.value&&editable)
        LanguageOptionsDialog(
            onDismiss = {
                optionDialog.value=false
            },
            onSelect = {
                language.value=it
                optionDialog.value=false

            }
        )
    Card(
        border = BorderStroke(1.dp, Color.Black),

        modifier = Modifier
            .fillMaxWidth()

            .background(Color.DarkGray),
        shape = RectangleShape

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = language.value,
                fontSize = 13.sp,
                color = when (language.value.lowercase()) {
                    "java" -> Color(0xFFB71C1C)        // Dark Red
                    "python" -> Color(0xFF0D47A1)      // Dark Blue
                    "javascript" -> Color(0xFFFFA000)  // Dark Yellow (Golden)
                    "kotlin" -> Color(0xFF006064)      // Dark Cyan-Teal
                    "c" -> Color(0xFF004D40)           // Darker Cyan-Green
                    "c++" -> Color(0xFF00796B)         // Dark Teal
                    else -> Color(0xFF1B5E20)          // Dark Green
                },
                modifier = Modifier
                    .padding(3.dp)
                    .clickable {
                        // TODO: Show language options
                        optionDialog.value = true
                    },
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (editable) "Paste" else "Copy",
                modifier = Modifier
                    .clickable(onClick = {
                    if(editable){

                        //TODO Paste the code from clip board
                        val text=clipManger.getText()?.text?:""
                        if(text.isNotEmpty()){
                            code.value=text
                        }

                    }
                    else{
                        println(code.value)
                        //if the mode is read
                        clipManger.setText(AnnotatedString(code.value))
                    }
                    })
                    .padding(3.dp),
                    fontSize = 13.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
            )

        }
    }
    CodeEditor(
        code = code,
        onCodeChange = {
            code.value=it
        //TODO apply changes
        },
        language = language
    )
}