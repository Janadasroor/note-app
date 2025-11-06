package com.example.myapplication.auth.TEST

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/*
Medium : 411 x 866 px
Medium : 411 x 866 px
Small : 360 x 592 px
 */
@Preview
@Composable

fun PrintSize(){
    Column {
        val config = LocalConfiguration.current

        val screenWidth = config.screenWidthDp
        val height = config.screenHeightDp

        Button(
            onClick = {

            }
        ) {
            Text(
                "Click me"
            )
        }
        Text(
            "Width is : ${screenWidth}"
        )
        Text(
            "Height is : ${height}"
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when{
                 screenWidth<411 ->Text(

                     "Small",
                     modifier = Modifier.align(Alignment.Center),
                     color = Color.Red
                 )

                else ->   Text(

                    "Big", color = Color.Green, modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }}