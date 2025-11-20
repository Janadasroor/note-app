package com.example.notex.TEST

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
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notex.R
import com.example.notex.utils.SelectableNoteText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@Composable
fun FakeNoteReadScreen() {
    val formattedDate = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault()).format(Date(System.currentTimeMillis()))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF003232), Color.LightGray) // Gradient background
                )
            )
            .padding(16.dp)
    ) {
        // 🔙 Back Button
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(40.dp)
                .background(Color.Black.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Note Title
        Text(
            text = "Note title",
            //text = note.title,
            fontSize = 26.sp,
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
               modifier =    Modifier.fillMaxWidth()
              )  {
                  SelectableNoteText("Java is a high-level, http://bb.com object-oriented programming language developed by Sun Microsystems in 1995. It is platform-independent, meaning code written in Java can run on any device with a Java Virtual Machine (JVM). Java is widely used for building web applications, mobile apps (especially Android), enterprise software, and large-scale systems. Its key features include strong memory management, automatic garbage collection, and multi-threading support. Java's \"write once, run anywhere\" philosophy, robust libraries, and frameworks like Spring and Hibernate make it a popular choice for developers. It is also known for its security, scalability, and extensive community support.")
               IconButton(
                   onClick = {},
                   modifier = Modifier.align(Alignment.TopEnd)
               )   {
                      Image(

                          modifier = Modifier

                              .size(20.dp),
                          imageVector = Icons.Filled.ContentCopy,
                          contentDescription = "Custom Icon",
                          colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.DarkGray)
                          )
                  }
              }
            }
        }
    }
}
