package com.example.pharmscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.example.pharmscan.ui.theme.PharmScanTheme
import kotlin.time.ExperimentalTime
import com.example.pharmscan.ui.Screen.FileIoExceptionScreen
import com.example.pharmscan.ui.Screen.NoFileFoundScreen
import com.example.pharmscan.ui.Screen.NoNetworkWarningScreen

class SystemMsgActivity : AppCompatActivity() {
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("coop", "enter sysmsg")
        val action = intent?.getAction()
        val content = intent?.getStringExtra("com.example.pharmscan.SYSTEM_MSG_CONTENT")

        setContent {
            when (action) {
                "NONETWORK" -> {
                    Log.d("coop", "NONETWORK: $content")
                    PharmScanTheme {
                        NoNetworkWarningScreen()
                    }
                }
                "NOFILEFOUND" -> {
                    Log.d("coop", "NOFILEFOUND: $content")
                    PharmScanTheme {
                        NoFileFoundScreen(content!!)
                    }
                }
                "COLLDATAEMPTY" -> {
                    Log.d("coop", "COLLDATAEMPTY: $content")
                    PharmScanTheme {
                        NoFileFoundScreen(content!!)
                    }
                }
                "FILEIOEXCEPTION" -> {
                    Log.d("coop", "FILEIOEXCEPTION: $content")
                    PharmScanTheme {
                        FileIoExceptionScreen(content!!)
                    }
                }
                "CLOSESYSACTIVITY" -> {
                    Log.d("coop", "CLOSESYSACTIVITY: $content")
                    finish()
                }
            }
        }
    }
}
