package com.example.myapplication

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel
import com.example.myapplication.RoomDb.NoteDatabase
import com.example.myapplication.presentation.ui.screen.AppNavHost
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel
import com.example.myapplication.RoomDb.viewmodel.ViewModelFactory
import com.example.myapplication.auth.data.userrepo.UserRepository
import com.example.myapplication.auth.domain.usecase.user.GetLoginInfoUseCase
import com.example.myapplication.auth.domain.usecase.user.LoadPostsUseCase
import com.example.myapplication.auth.domain.usecase.user.LogoutUseCase
import com.example.myapplication.auth.domain.usecase.user.UserUseClass
import com.example.myapplication.auth.viewmodel.UserViewModelFactory
import com.example.myapplication.settings.theme.MyAppTheme
import com.example.myapplication.utils.compose.NetworkMonitorToast
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val database = remember { NoteDatabase.getDatabase(this) }
            val systemUiController = rememberSystemUiController()
            val navigationBarColor =
                Color.Transparent  // Replace with your desired color

            SideEffect {
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor,
                    darkIcons = false // Set to true if you want dark icons on a light background
                )
            }
//            val serviceIntent = Intent(this, ForegroundService::class.java)
//            startForegroundService(serviceIntent)
            val context = LocalContext.current

            val application = context.applicationContext as Application

            val userRepo= UserRepository(context)
            val viewModelFactory = ViewModelFactory(database,context,userRepo)

            val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)

            // viewModel.addNote(title = "Java","Hello",0x002F50)
            //AppNavHost(viewModel)
            // FakeNoteReadScreen()
            val userUseClass = UserUseClass(
                getLoginInfoUseCase = GetLoginInfoUseCase(userRepo),
                loadPostsUseCase = LoadPostsUseCase(userRepo),
                logoutUseCase = LogoutUseCase(userRepo)
            )
            val userViewModelFactory=UserViewModelFactory(
                context = context
            )
            val userViewModel: UserViewModel = viewModel(factory =userViewModelFactory )
            val isLoaded by userViewModel.isLoaded.collectAsState()
            val secure = userViewModel.secureData.observeAsState()
            userViewModel.getLoginInfoFromStorage()

            //Logout
            val isLogin by userViewModel.isLoaded.collectAsState()
       //   userViewModel.logout()
//
//            if (!isLogin){
//                recreate()
//            }





            if (isLoaded) {
                NetworkMonitorToast()
                AppNavHost(
                    viewModel = viewModel,
                    userViewModel = userViewModel,
                    context = context
                )


            }
        else {
                // Loading UI while waiting for suspend function to finish
              LoadingScreen()

            }

            //  val googleAuthViewModel: GoogleAuthViewModel = viewModel() // ✅ Provide GoogleAuthViewModel

            //AppNavHost(noteViewModel)
            //GoogleSignInButton(viewModel = googleAuthViewModel) // ✅ Pass ViewModel
        }
    }


}

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace with your app logo if you want
            Icon(
                painter = painterResource(id = R.drawable.event_note),
                contentDescription = "Loading Logo",
                tint = Color.White.copy(alpha = alpha),
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Loading...",
                color = Color.White.copy(alpha = alpha),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

