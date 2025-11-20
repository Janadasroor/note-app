package com.example.notex.navigation.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notex.RoomDb.CodeBlock
import com.example.notex.RoomDb.models.ColorObject
import com.example.notex.ui.colors.ColorServices
import com.example.notex.ui.components.CodeBlockWidget
import com.example.notex.ui.components.ColorDropdownScreen


@Preview
@Composable

fun TestAddCodeNote(){
var codeBloks= remember { mutableStateOf<List<CodeBlock>>(listOf(

    CodeBlock(
        description = "Description",
        code = "Hellow()",
        language = "Java"
    )
)) }
    var codeList= remember { mutableStateListOf<CodeBlock>() }
    val index= remember { mutableStateOf(0) }
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }

    Column (
    modifier = Modifier
        .background(selectedColor.value.color)
        .fillMaxSize()
        .padding(top = 20.dp)
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        IconButton(
            onClick = {
              //  navController.popBackStack()
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
                //TODO insert code note

            },
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                .size(50.dp)
                .background(Color.White.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(Icons.Default.Check, contentDescription = "ADD", tint = Color.White)
        }
    }
    Text(
        text = "Title",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,

        color = Color.White,

        modifier = Modifier.padding(start = 10.dp)
    )
    Card  (
        shape = RectangleShape,
        modifier = Modifier.padding(4.dp)
        , colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        )

    ){
        ColorDropdownScreen(
            selectedColor = selectedColor
        )
    }
    Column (){
        LazyColumn {
            itemsIndexed(codeList)
            {
                i, codeBlok->

                var description = remember { mutableStateOf("Description") }
                var language = remember { mutableStateOf("Java") }
                var code = remember { mutableStateOf("Java()") }
                codeList.set(index = i, element =  CodeBlock(
                    description = description.value,
                    code = language.value,
                    language = code.value
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
        Button(
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
            shape = RectangleShape,
            modifier = Modifier.padding(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorServices().darkBlue
            )
        ) {
            Text(
                text = "Add Code Block"
            )
        }
    }
}
}