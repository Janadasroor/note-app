package com.example.notex.auth.presentation.ui.register
//

//import com.example.notex.auth.viewmodel.AuthViewModel
import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.auth.presentation.ui.theme.FontSServices
import com.example.notex.auth.presentation.ui.theme.alreadyFont
import com.example.notex.auth.presentation.ui.theme.sTxtFldFont
import com.example.notex.auth.presentation.ui.theme.titleFont
import com.example.notex.auth.presentation.ui.theme.txtFldFont
import com.example.notex.auth.utils.passwordRegex
import com.example.notex.auth.utils.rememberKeyboardVisibility
import com.example.notex.auth.viewmodel.RegisterViewModel
import com.example.notex.auth.viewmodel.UserViewModel

val textFieldHeight=40.dp

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(
//    showSystemUi = true,
//    device = "spec:width=720px,height=1440px,dpi=267"
//)

@SuppressLint("SuspiciousIndentation")
@Composable
fun SmallRegisterScreen(

    userViewModel: UserViewModel,

    navHome:NavController,
    navController: NavController,
    registerViewModel2: RegisterViewModel = viewModel()

) {

    val registerState by registerViewModel2.registerState.observeAsState()
  val isKeyboardVisible by  rememberKeyboardVisibility()
    val density= LocalDensity.current
    val fontServices= FontSServices(density)
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val registerMessage by remember { mutableStateOf("") }
    val errorPassword  by registerViewModel2.errorPassword.observeAsState()
    val errorEmail =registerViewModel2.errorEmail
    val errorName =registerViewModel2.errorName
    val passwordStatus = when {
        errorPassword != null -> errorPassword to Color.Red
        password.length !in 6..30 -> null // Ignore if the password length is invalid
        !password.matches(passwordRegex) -> if (password.length > 9)
            "Good password" to Color.Yellow
        else
            "Poor password" to Color.Red
        else -> "Strong password" to Color.Green
    }
  val scrollState= rememberScrollState()
    // Outer container with a vertical gradient background.
Column (
 modifier = Modifier
     .fillMaxSize()

    .verticalScroll(scrollState)
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
            .padding(start = 24.dp, top = 24.dp, bottom = 30.dp )

           ,
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


        // Card container for the form.
        Card(
            modifier = Modifier
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

                // Full Name Input

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    BasicTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            registerViewModel2.onNameChanged(it)
                        },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 12.sp, color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(textFieldHeight) // Keep height small
                            .border(
                                1.dp,
                                Color(0xFFBDBDBD),
                                RoundedCornerShape(12.dp)
                            ) // Border similar to OutlinedTextField
                            .padding(
                                horizontal = 12.dp,
                                vertical = 1.dp
                            ), // Adjust padding for proper alignment
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Person",
                                    tint = Color(0xFF757575)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {
                                    if (fullName.isEmpty()) {
                                        Text(
                                            "Full Name",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        ) // Placeholder text
                                    }
                                    innerTextField() // The actual text field input
                                }
                            }
                        }
                    )
                    if (errorName != null) Text(
                        errorName,
                        fontSize = 10.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(start = 10.dp)
                    )
// Email Input
                    BasicTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            registerViewModel2.onEmailChanged(it)
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
                            registerViewModel2.onPassChanged(it)
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
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = if(isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle Password Visibility"
                                    )
                                }
                            }
                        }
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
                    BasicTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            registerViewModel2.onPassChanged(it)
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
                            Column {
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
                                        if (confirmPassword.isEmpty()) {
                                            Text(
                                                text = "Confirm password",
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.align(Alignment.CenterStart) // Align placeholder text
                                            )
                                        }

                                        innerTextField() // Actual input field
                                    }
                                }

                            }
                        }
                    )
                    errorPassword?.let {
                        Text(
                            it,
                            fontSize = 10.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }


                }
                // Register Button with Animation
                Button(
                    onClick = {
                        if (password == confirmPassword) {
                            if (errorPassword != null && errorName != null && errorEmail != null) {
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
                            registerViewModel2.errorPassword.value = "Password fields are not the same"
                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(textFieldHeight)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
                ) {
                    HandleRegisterState(
                        userViewModel = userViewModel,
                        navController = navController,
                        context = context,
                        registerState = registerState
                    )
                }

                // Register Status Message
//                if (registerMessage.isNotEmpty()) {
//                    Text(
//                        text = registerMessage,
//                        color = Color.Gray,
//                        fontSize = fontServices.alreadyFont
//                    )
//                }

                // Login Navigation
                TextButton(onClick = {
                    navController.navigate("loginsmall")

                }) {
                    Text(
                        "Already have an account? Log in",
                        fontSize = alreadyFont,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

   //   Spacer(Modifier.height(if(isKeyboardVisible)200.dp else 0.dp))

}
