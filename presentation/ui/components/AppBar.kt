package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


//region App Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//TODO AppBar component
fun AppBar(showOptionDialog:MutableState<Boolean>,onDrawerButtonClick:()->Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onDrawerButtonClick()

                }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Add aa note", modifier = androidx.compose.ui.Modifier.size(30.dp)
                )
            }
        },
        title = {
            Text(
                text = "Note ++ ",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    showOptionDialog.value=true

                }
            ) {
                Icon(
                    tint = Color.White,
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add aa note", modifier = androidx.compose.ui.Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2A5298),
            titleContentColor = Color.White
        )
    )
}
//endregion
