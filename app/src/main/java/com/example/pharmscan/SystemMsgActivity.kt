package com.example.pharmscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pharmscan.ui.Screen.NoNetworkWarningScreen
import com.example.pharmscan.ui.theme.PharmScanTheme

class SystemMsgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            }
        }
    }
}
