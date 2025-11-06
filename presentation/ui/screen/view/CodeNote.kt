package com.example.myapplication.presentation.ui.screen.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.components.CodeBlockWidget
import com.example.myapplication.utils.sharePdf
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable

fun ReadCodeScreen(viewModel: NoteViewModel, navController: NavController){

    val selectedNote=viewModel.selectedNote
    if (selectedNote != null) {
        Column(
            modifier = Modifier
                .background(Color(selectedNote.color))
                .fillMaxSize()
                .padding(top = 20.dp)
        )  {
            val context= LocalContext.current
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                IconButton(
                    onClick = {
                        navController.popBackStack()
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

                        sharePdf(
                            context = context,
                            fileName = "${ selectedNote.title }.pdf",
                            note = selectedNote
                        )
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                        .size(50.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                }
            }


            Column {
                if (selectedNote != null) {
                    Text(
                        text = selectedNote.title.uppercase(Locale.ROOT),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,

                        color = Color.White,

                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                val codeBlocks= selectedNote?.codeBlocks
                if(codeBlocks!=null)
                    LazyColumn (
                        modifier = Modifier.padding(10.dp)
                    ) {
                        items(codeBlocks)
                        { codeBlock->
                            CodeBlockWidget(
                                description = mutableStateOf(codeBlock.description),
                                language = mutableStateOf(codeBlock.language),
                                code =  mutableStateOf(codeBlock.code),
                                editable = false
                            )
                        }
                    }
            }


        }
    }
}