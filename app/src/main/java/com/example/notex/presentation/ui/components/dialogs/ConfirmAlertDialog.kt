package com.example.notex.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

//region Beautiful Alert Dialog
//@Preview
@Composable
fun ConfirmAlertDialog(
    onConfirm:  () -> Unit,
    onDismiss: () -> Unit,
    title:String,
    msg:String
) {
    AlertDialog(
       // onDismissRequest = onDismiss,
        onDismissRequest = {onDismiss()},

        title = {
            Text(
                //text = "Exit App",
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
          // Text("Are you sure you want to exit the app?", style = MaterialTheme.typography.bodyMedium)
            Text(msg, style = MaterialTheme.typography.bodyMedium)
        },
        confirmButton = {
            Button(
             //   onClick = {},
              onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3C72))
            ) {
                Text("OK", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(

                           onClick = onDismiss
            ) {
                Text("Cancel", color = Color(0xFF1E3C72))
            }
        }
    )
}
//endregion