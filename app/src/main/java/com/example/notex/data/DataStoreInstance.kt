package com.example.notex.data


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Top-level declaration ensures a singleton per Context
val Context.dataStore by preferencesDataStore(name = "login_pref")
