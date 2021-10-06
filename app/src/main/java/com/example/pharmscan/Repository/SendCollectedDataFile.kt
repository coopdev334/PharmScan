package com.example.pharmscan.Repository

import android.util.Log
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.R
import com.example.pharmscan.ui.Utility.SystemMsg
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket

suspend fun repoSendCollectedDataFileToHost(hostIpAddress: String, hostServerPort: String, sendSysMsg: Boolean): Boolean {
    val requiredSuffixes = listOf("new", "NEW")
    var fileList = listOf<String>()
    var success = false

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
                    success = true
                }
            }

            File(fileName).delete()
            Log.d("coop", "successfull sending. Delete file")

        } catch (e: FileNotFoundException) {
            success = false
            Log.d("coop", "FileNotFoundException")
            Log.d("coop", e.message!!)
            if (sendSysMsg)
                SystemMsg("NOFILEFOUND", e.message!!)
        } catch (e: IOException) {
            success = false
            Log.d("coop", "IOException")
            Log.d("coop", e.message!!)
            if (sendSysMsg)
                SystemMsg("NONETWORK", e.message!!)
        }
    }

    return success
}