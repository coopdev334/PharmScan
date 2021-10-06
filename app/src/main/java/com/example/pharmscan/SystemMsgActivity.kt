package com.example.pharmscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.example.pharmscan.ui.Screen.*
import com.example.pharmscan.ui.Screen.NoFileFoundScreen
import com.example.pharmscan.ui.Utility.writeToFile
import com.example.pharmscan.ui.theme.PharmScanTheme
import java.util.*
import kotlin.time.ExperimentalTime

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
                    val time: Date = Calendar.getInstance().getTime()
                    writeToFile("Network Warning screen up at " + time.toString(), PharmScanApplication.context)
                    val hostIp = intent?.getStringExtra("com.example.pharmscan.SYSTEM_MSG_HOSTIP")
                    val hostPort = intent?.getStringExtra("com.example.pharmscan.SYSTEM_MSG_HOSTPORT")
                    PharmScanTheme {
                        NoNetworkWarningScreen(hostIp!!, hostPort!!)
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
                        CollDataTableEmptyScreen(content!!)
                    }
                }
                "FILEIOEXCEPTION" -> {
                    Log.d("coop", "FILEIOEXCEPTION: $content")
                    PharmScanTheme {
                        FileIoExceptionScreen(content!!)
                    }
                }
                "STARTEDFILEUPLOAD" -> {
                    Log.d("coop", "FILEIOEXCEPTION: $content")
                    PharmScanTheme {
                        StartedFileUploadScreen(content!!)
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
