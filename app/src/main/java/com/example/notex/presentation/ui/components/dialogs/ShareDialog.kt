package com.example.notex.ui.components.dialogs


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Composable
fun ShareDialog(
    onDismiss: () -> Unit,
    shareText: String
) {
    val context = LocalContext.current
    val scope= rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Share") },
        text = { Text("Select an option to share") },
        confirmButton = {
           Row() {
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share via"))
                    onDismiss()
                }) {
                    Text("Share")

                }
               TextButton(onClick = {
                   scope.launch(Dispatchers.IO) {
                       val uri = downloadAndSave(context, "https://wandersky.in/wp-content/uploads/2023/08/pxfuel-scaled.jpg")
                       uri?.let {
                           shareImage(context, it)
                       }
                   }
                   onDismiss()
                }) {
                    Text("Share a Image")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun downloadAndSave(context: Context,imageUrl: String):Uri?{
  return  try {
      val url = URL(imageUrl)
      val connection=url.openConnection()
      connection.connect()
      val inputStream = connection.getInputStream()
      val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "shared_image.jpg")
      val outputStream = FileOutputStream(file)
      inputStream.copyTo(outputStream)
      outputStream.close()
      inputStream.close()
      println("Downloading....")
      FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

  }catch (e:Exception){
      e.printStackTrace()
      println("Faled")
      null
  }
}
fun shareImage(context: Context, imageUri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, imageUri)
        println("Sending")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Share image via"))
}
@Preview
@Composable
fun PreviewShareDialog() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        ShareDialog(
            onDismiss = { showDialog = false },
            shareText = "Check out this cool app! 🚀"
        )
    }
}
