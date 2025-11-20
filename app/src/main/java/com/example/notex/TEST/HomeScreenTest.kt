package com.example.notex.TEST


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TestHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        AppBar()
        TopBodyBar()

        // Animated LazyColumn for smooth item animations
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(10) { note ->
                NoteItem(
                    modifier = Modifier
                )
            }
        }
    }
}





//region Top Body Bar
@Composable
private fun TopBodyBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "All Notes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2575FC)
        )
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "List View",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF2575FC)
            )
        }
    }
}

//endregion
