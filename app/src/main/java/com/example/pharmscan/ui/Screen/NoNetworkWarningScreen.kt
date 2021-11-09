package com.example.pharmscan.ui.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pharmscan.Repository.repoSendCollectedDataFileToHost
import com.example.pharmscan.ui.Utility.BackHandler
import com.example.pharmscan.ui.Utility.SystemMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun NoNetworkWarningScreen(hostIp: String, hostPort: String) {
    val elapsedMinutes = remember { mutableStateOf(0) }
    val closeNoNetScreen = remember { mutableStateOf(false) }
    val timelinePoint = LocalDateTime.now()

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            Log.d("coop", "launch timer")
            while (true) {
                delay(5000)
                val now = LocalDateTime.now()
                elapsedMinutes.value = java.time.Duration.between(timelinePoint, now).toMinutes().toInt()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
        Log.d("coop", "launch resend")
            closeNoNetScreen.value = ResendCollectedDataFile(hostIp, hostPort)
        }
    }

        if (closeNoNetScreen.value) {
            SystemMsg("CLOSESYSACTIVITY", "close activity")
            Log.d("coop", "calling CLOSESYSACTIVITY")

         }

        // Handle backstack and do nothing for warning screen consuming the
        // backstack button so warning screen stays in foreground
        BackHandler(true){
            Log.d("coop", "backstack")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "!!! WARNING !!!",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "No WiFi Network.",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Bring HH to gradle",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "to upload collected",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "data to host PC.",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 50.dp))
            Text(
                text = "Warning Display Time:",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            val mins = elapsedMinutes.value.toString()
            Text(
                text = "$mins minutes",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
        }
}

suspend fun ResendCollectedDataFile(hostIp: String, hostPort: String): Boolean {

    while (!repoSendCollectedDataFileToHost(hostIp, hostPort, sendSysMsg = false)) {
        delay(20000)
        Log.d("coop", "NoNetScreen trying send file again...")
    }

    return true
}
