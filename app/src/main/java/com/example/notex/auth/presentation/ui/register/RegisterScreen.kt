package com.example.notex.auth.presentation.ui.register

//import com.example.notex.auth.viewmodel.AuthViewModel
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.auth.presentation.ui.theme.FontSServices
import com.example.notex.auth.presentation.ui.theme.titleFont
import com.example.notex.auth.presentation.ui.theme.txtFldFont
import com.example.notex.auth.utils.passwordRegex
import com.example.notex.auth.viewmodel.RegisterViewModel
import com.example.notex.auth.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    userViewModel: UserViewModel,

    navHome:NavController,
    navController: NavController,

   // registerViewModel: AuthViewModel,
    registerViewModel2: RegisterViewModel = viewModel()
) {

    val density= LocalDensity.current
    val fontServices=FontSServices(density)
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val registerMessage by remember { mutableStateOf("") }
    val registerState by registerViewModel2.registerState.observeAsState()
    val errorPassword by registerViewModel2.errorPassword.observeAsState()
    val errorEmail =registerViewModel2.errorEmail
    val errorName =registerViewModel2.errorName
    val scope= CoroutineScope(Dispatchers.Main)
    val passwordStatus = when {
        errorPassword != null -> errorPassword to Color.Red
        password.length !in 6..30 -> null // Ignore if the password length is invalid
        !password.matches(passwordRegex) -> if (password.length > 9)
            "Good password" to Color.Yellow
        else
            "Poor password" to Color.Red
        else -> "Strong password" to Color.Green
    }
    // Outer container with a vertical gradient background.
    Box(
        modifier = Modifier
            .fillMaxSize()
            //TODO this is required for keyboard overlap problem
            .imePadding()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF3949AB), Color(0xFF283593))
                )
            )
    ) {
        Button(

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha =0.2f)
            ),
            modifier = Modifier
                .padding(start = 24.dp, top = 35.dp)

                .align(Alignment.TopStart),
            onClick = {
                // Perform your login logic here
                // After a successful login, navigate to the home screen
                navHome.navigate("home") {
                    popUpTo("auth") { inclusive = true }
                }
            }
        ) {
           Text("Skip")

        }
        // Card container for the form.
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                ,
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your actual logo
                    contentDescription = "Register Logo",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                // Title
                Text(
                    text = "Create an Account",
                    fontSize = titleFont,
                    color = Color(0xFF3949AB)
                )

                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    // Full Name Input
                    OutlinedTextField(
//                        supportingText = {
//                            registerViewModel2.errorName?.let { Text(it) }
//                        },
                        isError = errorName!=null,
                        maxLines = 1,
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            registerViewModel2.onNameChanged(it)
                        },
                        label = { Text("Full Name",fontSize= txtFldFont) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person",
                                tint = Color(0xFF757575)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(

                            focusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,

                        )
                    )
                    if (errorName != null) Text(
                        errorName,
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    // Email Input
                    OutlinedTextField(
                        maxLines = 1,

                        isError = errorEmail!=null,

                        value = email,
                        onValueChange = {
                            email = it
                            registerViewModel2.onEmailChanged(it)
                        },
                        label = {
                            Text(
                                text = "example@gmail.com",
                                fontSize = txtFldFont,
                            )

                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color(0xFF757575)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(

                            focusedTextColor = Color.Black,
                            cursorColor = Color(0xFF3949AB)
                        )
                    )
                    if (errorEmail != null) Text(
                        errorEmail,
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    // Password Input
                    OutlinedTextField(
                        maxLines = 2,
                        value = password,
                        isError = errorPassword!=null,

                        onValueChange = {
                            password = it
                            registerViewModel2.onPassChanged(it)
                        },
                        label = {
                            Text(
                                text = "Password",
                                fontSize = txtFldFont,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock",
                                tint = Color(0xFF757575)
                            )
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Password Visibility"
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(

                            focusedTextColor = Color.Black,
                            cursorColor = Color(0xFF3949AB)
                        )
                    )
                    passwordStatus?.let { (message, color) ->
                        if (message != null) {
                            Text(
                                text = message,
                                fontSize = 10.sp,
                                color = color,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                    // Confirm Password Input
                    OutlinedTextField(
                        maxLines = 2,

                        isError = errorPassword!=null,
//                        supportingText = {
//                            errorPassword?.let {
//                                Text(it)
//                            }
                    //    },
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = {
                            Text("Confirm Password",
                                fontSize = txtFldFont) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Lock",
                                tint = Color(0xFF757575)
                            )
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            cursorColor = Color(0xFF3949AB)
                        )
                    )
                    if (errorPassword != null) Text(
                        errorPassword!!,
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                // Register Button with Animation
                Button(
                    onClick = {

                            if (password == confirmPassword) {

                               runBlocking {
                                    registerViewModel2.onNameChanged(fullName)
                                    registerViewModel2.onPassChanged(password)
                                    registerViewModel2.onEmailChanged(email)
                                   scope.launch(Dispatchers.Main){
                                       registerViewModel2.errorName?.let { Log.e("errorName", it) }
                                       registerViewModel2.errorEmail?.let { Log.e("errorEmail", it) }
                                       registerViewModel2.errorPassword.value?.let {
                                           Log.e(
                                               "errorPassword",
                                               it
                                           )
                                       }
                                   }
                                }


                                if (errorPassword == null && errorName == null && errorEmail == null) {
                                    registerViewModel2.register(
                                        context = context,
                                        userViewModel = userViewModel,
                                        name = fullName,
                                        email = email,
                                        password = password,
                                        profilePicture = ""
                                    )
                                } else {
                                    registerViewModel2.onNameChanged(fullName)
                                    registerViewModel2.onPassChanged(password)
                                    registerViewModel2.onEmailChanged(email)

                                }
                            } else {
                                registerViewModel2.errorPassword.value =
                                    "Password fields are not the same"
                            }




                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
                ) {
                    HandleRegisterState(
                        userViewModel=userViewModel,
                        navController=navController,
                        context = context,
                        registerState = registerState
                    )
                }

                // Register Status Message
                if (registerMessage.isNotEmpty()) {
                    Text(
                        text = registerMessage,
                        color = Color.Gray,
                        fontSize = fontServices.alreadyFont
                    )
                }

                // Login Navigation
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    Text(
                        "Already have an account? Log in",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

