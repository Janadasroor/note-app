package com.example.myapplication.settings.datacontrols



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.settings.SettingPageH
import com.example.myapplication.settings.SettingsCheckboxItem

@Composable
fun DataControlsScreen(
    settNavController: NavController,
) {
    val context = LocalContext.current
    val iconSize = 25.dp
    val itemFontSize = 16.sp

    var isBackupChecked by remember { mutableStateOf(false) }
    var isDeleteChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        SettingPageH("Data Controls", onClick = {settNavController.popBackStack()})


        // Reusable items
        SettingsCheckboxItem(
            label = "Automatically Backup notes",
            isChecked = isBackupChecked,
            onCheckedChange = { isBackupChecked = it }
        )

        SettingsCheckboxItem(
            label = "Delete notes permanently",
            isChecked = isDeleteChecked,
            onCheckedChange = { isDeleteChecked = it }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DataControlsScreenPreview() {
    val context = LocalContext.current
    //  val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(context))

    DataControlsScreen (
        // viewModel = settingsViewModel,
        settNavController = NavController(context)
    )
}
