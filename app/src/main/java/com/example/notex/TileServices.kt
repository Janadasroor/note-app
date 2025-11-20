package com.example.notex

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Telephony.Mms.Intents
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import androidx.compose.ui.platform.InterceptPlatformTextInput

class MyQuickSettingsService : TileService() {

    override fun onStartListening() {
        qsTile?.apply {
            state = Tile.STATE_ACTIVE
            label = "Note"
            updateTile()
        }
    }

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        Toast.makeText(applicationContext, "clicked!", Toast.LENGTH_SHORT).show()

        // Optionally, you can open an activity:
        val intent= Intent(this,MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivityAndCollapse(intent)
    }
}
