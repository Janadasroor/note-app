import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.auth.presentation.ui.register.HandleVerificationState
import com.example.myapplication.auth.viewmodel.RegisterViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun VerificationScreen(
    userViewModel: UserViewModel,
    navLogin:NavController,
    authViewModel:RegisterViewModel= viewModel()) {
    val digitCount = 6
    // Holds each digit's input as a mutable state list.
    val code = remember { mutableStateListOf("", "", "", "", "", "") }
    // Create a focus requester for each digit field.
    val focusRequesters = List(digitCount) { remember { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current
    val registerState by authViewModel.registerState.observeAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFF3949AB))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification Code",
                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Create 6 individual digit input fields.
                for (i in 0 until digitCount) {
                    OutlinedTextField(
                        value = code[i],
                        onValueChange = { value ->
                            // Allow only one digit and only numbers.
                            if (value.length <= 1 && value.all { it.isDigit() }) {
                                code[i] = value
                                // If a digit is entered, move focus to the next field.
                                if (value.length == 1 && i < digitCount - 1) {
                                    focusRequesters[i + 1].requestFocus()
                                }
                                // If the user clears a field, optionally move focus back.
                                if (value.isEmpty() && i > 0) {
                                    focusRequesters[i - 1].requestFocus()
                                }
                                // Optionally hide the keyboard when the last digit is entered.
                                if (i == digitCount - 1 && value.isNotEmpty()) {
                                    keyboardController?.hide()
                                }
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .size(56.dp)
                            .focusRequester(focusRequesters[i]),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            containerColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Button(
                onClick = {
                    // Example log output, update with your desired message.
                    Log.e("msg", code.joinToString(separator = ""))

                    authViewModel.sendCodeVerification(code.joinToString(separator = ""),context,userViewModel)
                },
                modifier = Modifier.padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                HandleVerificationState(
                    navController = navLogin,
                    context = context,
                    registerState = registerState
                )

            }
            TextButton(
                onClick = {
                    authViewModel.resendCodeVerification(email = userViewModel.email.value, context = context)
                }
            ) {
                Text(
                    text = "Resend verification code .",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            Text(
                text = "userViewModel.refreshToken.value",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}
