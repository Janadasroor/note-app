package com.example.myapplication.auth.TEST
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun KeyboardSafeScreen() {


    val height=5.dp
    // Automatically adds padding at the bottom when keyboard appears
  Box(
      modifier = Modifier
          .fillMaxSize()
          .imePadding() // adds padding to avoid overlap
          .padding(16.dp)
  )  {
     Card   {
          Column(
              verticalArrangement = Arrangement.spacedBy(5.dp),
              horizontalAlignment = Alignment.CenterHorizontally

          ) {
              Text(text = "Enter your info")

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Username") },
                  modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Password") },
                  modifier = Modifier.fillMaxWidth()
              )



              Text(text = "Enter your info")

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Username") },
                  modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Password") },
                  modifier = Modifier.fillMaxWidth()
              )



              Text(text = "Enter your info")

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Username") },
                  modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(height))

              OutlinedTextField(
                  value = "",
                  onValueChange = {},
                  label = { Text("Password") },
                  modifier = Modifier.fillMaxWidth()
              )



              Button(
                  onClick = {},
                  modifier = Modifier.align(Alignment.CenterHorizontally)
              ) {
                  Text("Submit")
              }
          }
      }
    }
}
