package com.example.myapplication.settings.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun  AlertItemText(text:String){

    Text(text = text, fontWeight = FontWeight.Bold, fontSize = 13.sp)

}


@Composable
fun  AlertItemTextH2(text:String){

    Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp,)


}
@Composable
fun  AlertItemTextH1(text:String){

    Text(text, fontSize = 15.sp, fontWeight = FontWeight.Bold)

}

