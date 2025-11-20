package com.example.notex.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notex.R
import com.example.notex.settings.theme.MyAppTheme
import com.example.notex.settings.theme.ThemeType


@SuppressLint("SuspiciousIndentation")
@Composable
fun SettingsScreen(
    navController: NavController,
    // settNavController:NavController,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
//    val isSyncEnabled by viewModel.isSyncEnabled.collectAsState()
//    val user by viewModel.user.collectAsState()
//    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val iconSize=25.dp
    val itemFontSize=16.sp
    LaunchedEffect(Unit) {
        //viewModel.loadUser(context)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()

            .padding(top = 30.dp, start = 10.dp)

        , verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        SettingPageH("Settings", onClick = {navController.popBackStack()})

        Row(
            modifier = Modifier
                .height(70.dp)

                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile Picture",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(25.dp)
            )


            Spacer(modifier = Modifier.width(12.dp))

            Text("Name", fontSize = itemFontSize, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)

        }
        Settingsitem(
            icon = Icons.Outlined.Email,
            name = "Email",
            onTap = {},
            contentDescription = "Email"
        )
        Settingsitem(
            icon = Icons.Outlined.Palette,
            name = "Personalization",
            onTap = {
                navController.navigate("settings/person")
            },
            contentDescription = "Personalization"
        )
        Settingsitem(
            icon = Icons.Outlined.Storage,
            name = "Data Controls",
            onTap = {
                navController.navigate("settings/datacontrols")
            },
            contentDescription = "Data Settings"
        )
        Settingsitem(
            icon = Icons.Outlined.Security,
            name = "Security",
            onTap = {},
            contentDescription = "Security Settings"
        )


        Divider(modifier = Modifier.padding(vertical = 16.dp))


        Button  (
            onClick = {

            }
            , contentPadding = PaddingValues(0.dp)
            , shape = RectangleShape
            , colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        )  {
            Row(
                modifier = Modifier
                    .height(70.dp)

                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Sign out",
                    tint = Color.Red,
                    modifier = Modifier.size(iconSize)
                )


                Spacer(modifier = Modifier.width(12.dp))

                Text("Sign out", fontSize = itemFontSize, color = MaterialTheme.colorScheme.onBackground)
            }
        }


//        user?.let {
//            Text("👤 Logged in as:")
//            Text("Name: ${it.name}")
//            Text("Email: ${it.email}")
//        }




        // === Appearance Settings ===
//        Text("🎨 Appearance", style = MaterialTheme.typography.titleMedium)
//
//        ThemeSelector(
//            selectedTheme = selectedTheme,
//            onThemeSelected = viewModel::setTheme
//        )
//
//        Divider(modifier = Modifier.padding(vertical = 16.dp))
//
//        // === Logout ===
//        Button(
//            onClick = {
//                viewModel.logout(context, onLogout)
//            },
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
//        ) {
//            Text("🚪 Logout")
//        }
    }
}



@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
    // val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(context))
    val themeType = remember {  mutableStateOf(ThemeType.LIGHT) }
    MyAppTheme(
        themeType =themeType
    ) {
        Surface  (
            modifier = Modifier.fillMaxSize(),
//           color = MaterialTheme.colorScheme.background
        ) {
            SettingsScreen(
                //   viewModel = settingsViewModel,
                onLogout = {},
                navController = NavController(context),
                //  settNavController = NavController(context)
            )
        }
    }
}