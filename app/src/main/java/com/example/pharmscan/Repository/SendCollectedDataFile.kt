package com.example.pharmscan.Repository

import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.R
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.SystemMsg
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket

suspend fun repoSendCollectedDataFileToHost(hostIpAddress: String, hostServerPort: String) {
    val requiredSuffixes = listOf("new")
    var fileList = listOf<String>()

    fun hasRequiredSuffix(file: File): Boolean {
        return requiredSuffixes.contains(file.extension)
    }

    val filePath = PharmScanApplication.context?.getString(R.string.collected_data_file_path)
    File(filePath!!).walkTopDown().filter { file ->
        file.isFile && hasRequiredSuffix(file)
    }.forEach {
        fileList = fileList + it.absolutePath
        Log.d("coop", it.absolutePath)
    }

    Log.d("coop", fileList.size.toString())

    for (fileName in fileList) {
        Log.d("coop", "sending $fileName")
        try {
            Socket(hostIpAddress, hostServerPort.toInt()).use { soc ->
                PrintWriter(soc.getOutputStream(), true).use { writer ->
                    writer.println(fileName.substringAfterLast("/"))
                    File(fileName).forEachLine { line ->
                        writer.println(line)
                    }
                    writer.println("END OF FILE")
                }
            }

            File(fileName).delete()
            Log.d("coop", "successfull sending. Delete file")

        } catch (e: FileNotFoundException) {
            Log.d("coop", "FileNotFoundException")
            Log.d("coop", e.message!!)
            SystemMsg("NOFILEFOUND", e.message!!)
        } catch (e: IOException) {
            Log.d("coop", "IOException")
            Log.d("coop", e.message!!)
            SystemMsg("NONETWORK", e.message!!)
        }
    }
}