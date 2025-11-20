package com.example.notex.TEST


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notex.TEST.CodeExample
import com.example.notex.ui.components.CodeEditor

@Preview
@Composable

fun TestReadCodeScreen(){
    var code by remember { mutableStateOf("" +
            CodeExample().codeExample) }
    Column(
        modifier = Modifier
            .background(Color.Blue.copy(alpha = 0.9f))
            .fillMaxSize()
            .padding(top = 20.dp)
    )  {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
            }
        }
        LazyColumn(

        ) {
            items(10) {
                Column {
                    Text(
                        text = "TITLE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,

                        color = Color.White,

                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Card(
                            shape = RectangleShape,
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        ){
                            SelectionContainer  {
                                Text(
                                    text = CodeExample().description,
                                    modifier = Modifier.padding( 10.dp)
                                )
                            }
                        }
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
                                    text = "Kotlin",
                                    fontSize = 13.sp,
                                    color = Color.Red,
                                    modifier = Modifier.padding(3.dp),

                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Copy",
                                    fontSize = 13.sp,
                                    color = Color.Blue,
                                    modifier = Modifier.padding(3.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                     //   CodeEditor(code = code, onCodeChange = { code = it }, language = "kotlin")

                    }
                }
            }
        }
    }
}