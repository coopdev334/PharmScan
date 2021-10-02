package com.example.pharmscan.Repository

import android.util.Log
import com.example.pharmscan.Data.Tables.HostIpAddress
import kotlinx.coroutines.runBlocking
import java.io.*
import java.net.Socket
import java.nio.file.Files.delete

fun SendCollectedDataFileToHost(hostIpAddress: String, hostServerPort: String) {
    val requiredSuffixes = listOf("new")
    var fileList = listOf<String>()

    fun hasRequiredSuffix(file: File): Boolean {
        return requiredSuffixes.contains(file.extension)
    }

    File("/sdcard/Download/").walkTopDown().filter { file ->
        file.isFile && hasRequiredSuffix(file)
    }.forEach {
        fileList = fileList + it.absolutePath
        Log.d("coop", it.absolutePath)
    }

    Log.d("coop", fileList.size.toString())

    for (fileName in fileList) {
        Log.d("coop", "sending")
        try {
            Socket(hostIpAddress, hostServerPort.toInt()).use { soc ->
                PrintWriter(soc.getOutputStream(), true).use { writer ->
                    File(fileName).forEachLine { line ->
                        writer.println(line)
                    }
                }
            }

            File(fileName).delete()
            Log.d("coop", "successfull sending. Delete file")

        } catch (e: FileNotFoundException) {
            Log.d("coop", "FileNotFoundException")
            Log.d("coop", e.message!!)
        } catch (e: IOException) {
            Log.d("coop", "IOException")
            Log.d("coop", e.message!!)
        }
    }
}