package com.example.myapplication.presentation.ui.screen.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.utils.GoogleAuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Preview
@Composable
fun LoginScreen(viewModel: GoogleAuthViewModel = viewModel()) {
    GoogleSignInButton(viewModel)
}



@Composable
fun GoogleSignInButton(viewModel: GoogleAuthViewModel) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        viewModel.handleSignInResult(task) // ✅ Pass the task directly
     }

    Button(onClick = { launcher.launch(viewModel.googleSignInClient.signInIntent) }) {
        Text("Sign in with Google")
    }
}

