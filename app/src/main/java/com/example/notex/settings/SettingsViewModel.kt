package com.example.notex.settings//package com.example.notex.settings
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import com.example.notex.auth.data.LoginPrefModel
//import com.example.notex.auth.data.SecureLoginDataStoreServices
//import com.example.notex.auth.data.userrepo.UserRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class SettingsViewModel(
//    private val userRepo: UserRepository,
////    private val noteRepo: NoteRepository
//) : ViewModel() {
//
//    private val _isSyncEnabled = MutableStateFlow(true)
//    val isSyncEnabled: StateFlow<Boolean> = _isSyncEnabled
//
//    private val _user = MutableStateFlow<LoginPrefModel?>(null)
//    val user: StateFlow<LoginPrefModel?> = _user
//    private val _selectedTheme = MutableStateFlow("System")
//    val selectedTheme: StateFlow<String> = _selectedTheme
//
//    fun setTheme(theme: String) {
//        _selectedTheme.value = theme
//        // persist theme choice if needed
//    }
//    fun toggleSync(enabled: Boolean) {
//        _isSyncEnabled.value = enabled
//        // optionally persist this setting
//    }
//
//    fun syncNow() {
//        viewModelScope.launch {
//            try {
//              //  noteRepo.syncNotes()
//            } catch (e: Exception) {
//                Log.e("Settings", "Sync failed: ${e.message}")
//            }
//        }
//    }
//
//    fun loadUser(context: Context) {
//        viewModelScope.launch {
//            val login = SecureLoginDataStoreServices(context).getLoginInfo()
//            _user.value = login
//        }
//    }
//
//    fun logout(context: Context, onLogout: () -> Unit) {
//        viewModelScope.launch {
//            userRepo.logout()
//            onLogout()
//        }
//    }
//}
//class SettingsViewModelFactory(
//    private val context: Context,
//
//
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
//           val userRepo=UserRepository(context)
//            return SettingsViewModel(userRepo) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}