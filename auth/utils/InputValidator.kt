package com.example.myapplication.auth.utils

import android.util.Patterns
import kotlinx.coroutines.delay
import java.util.regex.Pattern


// Function to validate email
suspend fun validateEmail(email: String): String? {
     delay(500)
    return when {
        email.isEmpty()->"Email is required"

        !Patterns.EMAIL_ADDRESS.matcher(email).matches()->"Invalid email format"

        !email.endsWith("@gmail.com")->"is not Gmail account"
        else ->null
    }
}
val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$")

suspend fun validatePass(password: String): String? {
     delay(500)

    return when {
        password.isEmpty()->"Enter a Password"
        password.length<6 ->"Password is less than 6 characters"
         else ->null
    }
}

// Function to validate name (no numbers or special characters)
fun validateName(name: String): String? {
    val namePattern = Pattern.compile("^[a-zA-Z ]+\$")
    return if (name.isEmpty()) {
        "Name is required"
    } else if (!namePattern.matcher(name).matches()) {
        "Name must contain only letters"
    } else {
        null
    }
}