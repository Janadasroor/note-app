package com.example.notex

import kotlinx.coroutines.*
suspend fun fetchData(): String {
    delay(2000L)  // Simulate a network delay
    return "Data fetched !!"
}
fun main() = runBlocking { // This creates a coroutine scope for the main thread
    launch {  // Start a new coroutine

    }
    println(fetchData())
}
