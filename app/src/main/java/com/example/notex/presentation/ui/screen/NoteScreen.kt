package com.example.notex.presentation.ui.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.RoomDb.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun NoteReadScreen(navController: NavController, viewModel: NoteViewModel) {
val clipManger= LocalClipboardManager.current
    val note = viewModel.selectedNote
    val formattedDate = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault()).format(
        note?.timestamp ?: System.currentTimeMillis()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(note?.color ?: 0x323232),
                        Color(0xFF1E1E1E)
                    )
                )
            )
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
    ) {
        // 🔙 Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Note Title
        Text(
            text = note?.title ?: "Untitled Note",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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

        // 📜 Note Content
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Make note content scrollable
                    SelectionContainer {
                        androidx.compose.foundation.rememberScrollState().let { scrollState ->
                            androidx.compose.foundation.layout.Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 20.dp)
                                    .verticalScroll(scrollState)
                            ) {
                                Text(
                                    text = note?.content ?: "No content available",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF333333)
                                )
                            }
                        }
                    }

                    // Copy button
                    IconButton(
                        onClick = {
                            clipManger.setText(annotatedString = AnnotatedString(note?.content?:""))
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

