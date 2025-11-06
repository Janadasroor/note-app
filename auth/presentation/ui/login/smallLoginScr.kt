package com.example.myapplication.auth.presentation.ui.login



import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.presentation.ui.register.textFieldHeight
import com.example.myapplication.auth.presentation.ui.theme.alreadyFont
import com.example.myapplication.auth.presentation.ui.theme.titleFont
import com.example.myapplication.auth.utils.rememberKeyboardVisibility
//import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.LoginVm
import com.example.myapplication.auth.viewmodel.UserViewModel


@Composable
fun SmallLoginScreen(
    loginVm: LoginVm,
    userViewModel :UserViewModel,
    navController: NavController,
    navHome: NavController
) {
    val isKeyboardVisible by  rememberKeyboardVisibility()

    val context = LocalContext.current
    val loginState by loginVm.loginState.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf("") }
    var errorPassword =loginVm.errorPassword
    var errorEmail =loginVm.errorEmail

    Column (
        modifier = Modifier   .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()

            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF3949AB), Color(0xFF283593))
                )
            )
    ){
        Button(

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.2f)
            ),
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp, bottom = 30.dp),
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
        Box(


        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
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
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // App Logo
                    Image(
                        painter = painterResource(id = R.drawable.app_icon), // Replace with your actual logo
                        contentDescription = "Register Logo",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )


                    // Title
                    Text(
                        text = "Welcome Back!",
                        fontSize = titleFont,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF3949AB)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),

                        )
                    {
                        BasicTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                loginVm.onEmailChanged(it)
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(textFieldHeight)
                                .border(
                                    1.dp,
                                    if (errorEmail != null) Color(0xFFF51313) else Color(0xFFBDBDBD),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Email",
                                        tint = Color(0xFF757575),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box {
                                        if (email.isEmpty()) {
                                            Text(
                                                text = "example@gmail.com",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.align(Alignment.CenterStart) // Align placeholder text
                                            )
                                        }
                                        innerTextField() // Actual input field
                                    }

                                }
                            }
                        )
                        if (errorEmail != null) Text(
                            errorEmail,
                            fontSize = 10.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(start = 10.dp)
                        )

// Password Input
                        BasicTextField(
                            value = password,
                            onValueChange = {
                                password = it
                                loginVm.onPassChanged(it)
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(textFieldHeight)
                                .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            decorationBox = { innerTextField ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Lock",
                                        tint = Color(0xFF757575),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box {
                                        if (password.isEmpty()) {
                                            Text(
                                                text = "Password",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.align(Alignment.CenterStart) // Align placeholder text
                                            )
                                        }
                                        innerTextField() // Actual input field
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    IconButton(onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }) {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(
                                                id = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                            ),
                                            contentDescription = "Toggle Password Visibility"
                                        )
                                    }
                                }
                            }
                        )
                        if (errorPassword != null) Text(
                            errorPassword,
                            fontSize = 10.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                    }


                    // Login Button with Animation
                    Button(
                        onClick = {
                            if (errorEmail != null && errorPassword != null) {
                                loginVm.login(
                                    email = email,
                                    password = password,
                                    profilePicture = "",
                                    context = context,
                                    userViewModel = userViewModel

                                )
                            } else {
                                loginVm.onEmailChanged(email)
                                loginVm.onPassChanged(password)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(textFieldHeight)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
                    ) {
                        HandleLoginState(
                            navLogin = navController,
                            navHome = navHome,
                            context = context,
                            loginState = loginState
                        )
                    }


                    // Login Status Message
//               if (loginMessage.isNotEmpty()) {
//                   Text(text = loginMessage, color = Color.Gray, fontSize = 14.sp)
//               }


                    // Register Navigation
                    TextButton(
                        onClick = {
                            /*TODO Navigate to Register Screen */
                            navController.popBackStack()
                        }) {
                        Text(
                            "Don't have an account? Sign Up",
                            fontSize = alreadyFont,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

        }
//        if(isKeyboardVisible){
//            Spacer(Modifier.height( 200.dp ))
//
//
//
//        }
//        LaunchedEffect(isKeyboardVisible) {
//            Log.e("error","Keyboard is visible: $isKeyboardVisible")
//            Toast.makeText(context,"Fill the field .",Toast.LENGTH_LONG).show()
//        }

    }
}
