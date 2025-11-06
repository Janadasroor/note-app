//package com.example.myapplication.auth.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.launch
//
//class AuthViewModel:ViewModel() {
//
//    val auth: FirebaseAuth = Firebase.auth
//    fun signUp(
//        email:String,password:String,
//        name:String="",onSuccess:()->Unit,
//        onError:(msg:String)->Unit){
//
//        viewModelScope.launch {
//            auth.createUserWithEmailAndPassword(
//              email,password
//            )
//            .addOnCompleteListener{
//                t ->
//                if (t.isSuccessful) {
//                    onSuccess()
//                } else {
//                    onError(t.exception?.message ?: "Sign-up failed")
//                }
//            }
//
//
//
//
//
//        }
//    }
//    fun login(
//        email:String,password:String,
//        name:String="",onSuccess:()->Unit,
//        onError:(msg:String)->Unit){
//
//    }
//}