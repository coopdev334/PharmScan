package com.example.pharmscan.ui.Utility

import android.content.Intent
import com.example.pharmscan.PharmScanApplication

fun SystemMsg (msgType: String, content: String) {
    val psApp = PharmScanApplication()
    val dwIntent = Intent()

    dwIntent.action = "com.example.pharmscan.SYSTEM_MSG"
    dwIntent.putExtra("com.example.pharmscan.SYSTEM_MSG_TYPE", msgType)
    dwIntent.putExtra("com.example.pharmscan.SYSTEM_MSG_CONTENT", content)
    psApp.getAppContext()?.sendBroadcast(dwIntent)
}