package com.example.pharmscan.ui.Navigation

import android.media.AudioAttributes
import android.media.RingtoneManager
import android.media.SoundPool
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.BuildConfig
import com.example.pharmscan.PharmScanApplication
import android.media.ToneGenerator

import android.media.AudioManager




fun NavGraphBuilder.addAboutScreen(navController: NavController) {


    composable(Screen.AboutScreen.route) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
            }
            Text(
                text = "<-",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "PharmScan",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Version: " + BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Unit Id: 0E89H67",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(height = 100.dp))
            Text(
                text = "By: coopdev334",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Test Sound",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        // Sound Stuff
                        /*
                        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        val r = RingtoneManager.getRingtone(PharmScanApplication.context, notification)
                        r.play()
                        */
                        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)

                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "Test Three Times",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 20.dp)
                    .clickable {
                        // Sound Stuff
                        /*
                        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        val r = RingtoneManager.getRingtone(PharmScanApplication.context, notification)
                        r.play()
                        */
                        val toneGG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                        toneGG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000)


                    },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
        }

    }
}