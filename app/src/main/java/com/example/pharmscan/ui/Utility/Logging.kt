package com.example.pharmscan.ui.Utility

import android.content.Context
import android.util.Log
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

public fun writeToFile(data: String, context: Context?) {
    if (context != null) {
        val path: String = "/sdcard/Download/"//context!!.getExternalFilesDir(null)
        Log.d("TESTING", path.toString())
        val file: File? = File(path, "PharmaScanLogs.txt")
        var content = data + "\n"
        if (file != null) {
            if (file.exists()) {
                Files.write(file.toPath(), content.toByteArray(), StandardOpenOption.APPEND)
            }
            else {
                file.createNewFile()
                Files.write(file.toPath(), content.toByteArray(), StandardOpenOption.APPEND)
            }
        }
    }
}


// /sdcard/Download/psndc.dat
