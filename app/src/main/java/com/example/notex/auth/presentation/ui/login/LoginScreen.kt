package com.example.notex.auth.presentation.ui.login


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.auth.presentation.ui.theme.alreadyFont
import com.example.notex.auth.presentation.ui.theme.titleFont
import com.example.notex.auth.presentation.ui.theme.txtFldFont
//import com.example.notex.auth.viewmodel.AuthViewModel
import com.example.notex.auth.viewmodel.LoginVm
import com.example.notex.auth.viewmodel.LoginVmFact
import com.example.notex.auth.viewmodel.UserViewModel


@Composable
fun LoginScreen(
    loginVm: LoginVm,
    userViewModel: UserViewModel,
    navController: NavController,
    navHome: NavController
) {
    val context = LocalContext.current
    val loginState by loginVm.loginState.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf("") }
    var errorPassword =loginVm.errorPassword
    var errorEmail =loginVm.errorEmail

    Box(
       modifier = Modifier
           .fillMaxSize()
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
     Card (
         modifier = Modifier
             .fillMaxWidth()
             .padding(horizontal = 24.dp)
             .align(Alignment.Center)
         ,
         shape = RoundedCornerShape(24.dp),
         elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
         colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))

     )  {
           Column(
               modifier = Modifier
                   .padding(24.dp)
                   .verticalScroll(rememberScrollState())
                   .animateContentSize(
                       animationSpec = tween(
                           durationMillis = 500,
                           easing = LinearOutSlowInEasing
                       )
                   ),
               verticalArrangement = Arrangement.spacedBy(1.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               // App Logo
               Image(
                  imageVector = Icons.Filled.Login, contentDescription = "Register Logo",
                   modifier = Modifier
                       .size(70.dp)
                       .clip(RoundedCornerShape(16.dp))
               )

               Spacer(modifier = Modifier.height(20.dp))

               // Title
               Text(
                   text = "Welcome Back!",
                   fontSize = titleFont,
                   style = MaterialTheme.typography.headlineMedium,
                   color = Color(0xFF3949AB)
               )

               Spacer(modifier = Modifier.height(4.dp))

               // Email Input
               OutlinedTextField(
                    supportingText = {
                        loginVm.errorEmail?.let { Text(it) }
                                     },
                   isError = errorEmail!=null,
                   value = email,
                   onValueChange = {
                       email = it
                       loginVm.onEmailChanged(it)
                                   },
                   label = {
                       Text("example@gmail.com",fontSize = txtFldFont)

                   },
                   leadingIcon = {
                       Icon(
                           imageVector = Icons.Default.Email,
                           contentDescription = "Email"
                       )
                   },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                   shape = RoundedCornerShape(12.dp),
                   modifier = Modifier.fillMaxWidth()

               )




               // Password Input
               OutlinedTextField(
                   isError = loginVm.errorPassword!=null,
                   value = password,
                   supportingText = {
                       errorPassword?.let {
                           Text(it)
                       }
                   },
                   onValueChange = {
                       password = it
                       loginVm.onPassChanged(it)

                                   },
                   label = {
                       Text("Password", fontSize = 15.sp)

                   },
                   leadingIcon = {
                       Icon(
                           imageVector = Icons.Default.Lock,
                           contentDescription = "Lock"
                       )
                   },
                   visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                   trailingIcon = {
                       IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                           Icon(
                               modifier = Modifier.size(30.dp),
                               imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                               contentDescription = "Toggle Password Visibility"
                           )
                       }
                   },
                   shape = RoundedCornerShape(12.dp),
                   modifier = Modifier.fillMaxWidth()
               )

               Spacer(modifier = Modifier.height(9.dp))

               // Login Button with Animation
               Button(
                   onClick = {
                   if(errorEmail==null&&errorPassword==null){
                       loginVm.login(
                           email = email,
                           password = password,
                           profilePicture = "",
                           context = context,
                           userViewModel = userViewModel
                       )
                   }
                       else {
                           loginVm.onEmailChanged(email)
                           loginVm.onPassChanged(password)
                   }

                 },
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(50.dp)
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

               Spacer(modifier = Modifier.height(12.dp))

               // Login Status Message
//               if (loginMessage.isNotEmpty()) {
//                   Text(text = loginMessage, color = Color.Gray, fontSize = 14.sp)
//               }

               Spacer(modifier = Modifier.height(5.dp))

               // Register Navigation
               TextButton(
                   onClick = {
                   /*TODO Navigate to Register Screen */
                   navController.navigate("add")
               }) {
                   Text("Don't have an account? Sign Up", fontSize = alreadyFont, color = MaterialTheme.colorScheme.primary)
               }
           }
       }
    }
}
