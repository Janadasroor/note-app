package com.example.notex.auth.TEST

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.notex.auth.data.MediaPost
import com.example.notex.auth.viewmodel.UserViewModel
import com.example.notex.ui.components.dialogs.ConfirmAlertDialog
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Preview
@Composable
fun TestDataStore(userViewModel: UserViewModel= viewModel()) {

    Column(modifier = Modifier.padding(16.dp))
    {
        var show by remember { mutableStateOf(false) }
        val context= LocalContext.current
        val isLoaded = remember { mutableStateOf(false) }
        //It is linked directly to LiveData of secureData
        val data by  userViewModel.secureData.observeAsState()

        Text(text = "Hello, DataStore!")
        Button(

            onClick = {

                // Handle button click here
                userViewModel.getLoginInfoFromStorage()

            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Click Me")
        }
          Button(

            onClick = {


                // Handle button click here
                data?.let {
                    userViewModel.loadData( it.accessToken) }
               show=true
               },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Load content")
        }
        Text(text = "Refresh token :    ${data?.token}")
        Spacer(Modifier.height(10.dp))
        Text(text = "Email :    ${data?.email}")
        Spacer(Modifier.height(10.dp))
        Text(text = "User`s id :    ${data?.id}")
        Spacer(Modifier.height(10.dp))
        Text(text = "Access token:    ${data?.accessToken}")
        Spacer(Modifier.height(10.dp))
        Text(text = "Content : ${userViewModel.postsString.value}")
       if(show) {
           ConfirmAlertDialog(
               onConfirm = { show = false },
               onDismiss = { show = false },
               title = "Res",
               msg =" userViewModel.contentLoadMsg.value"
           )
       }

        PostListScreen(userViewModel)
        }


}






@Composable
fun PostListScreen(userViewModel: UserViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        userViewModel.posts.value?.let { response ->
            items(response) { post ->
                PostCard(post = post)
            }
        }
    }
}

@Composable
fun PostCard(post: MediaPost) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // User info section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = post.profilePicture,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = post.name,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = post.created_at.formatDate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content section
            when (post.content_type) {
                "image" -> {
                    AsyncImage(
                        model = post.content_url,
                        contentDescription = post.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                "video" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .background(Color.Black)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        // Here you would use your video player component
                        AsyncImage(
                            model = post.thumbnail_url,
                            contentDescription = "Video thumbnail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play video",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
                else -> {
                    // For posts or other content types
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Post Content")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            if (post.description.isNotEmpty()) {
                Text(
                    text = post.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Metadata section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Likes
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Likes",
                        tint = if (post.like_count > 0) Color.Red else Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.like_count.toString())
                }

                // Comments
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Comments",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.comment_count.toString())
                }

                // Shares
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Shares",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = post.shares_count.toString())
                }
            }
        }
    }
}

// Extension function to format the date
fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: this)
    } catch (e: Exception) {
        this
    }
}