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

fun repoSendCollectedDataFileToHost(hostIpAddress: String, hostServerPort: String, sendSysMsg: Boolean): Boolean {
    val requiredSuffixes = listOf("new", "NEW")
    var fileList = listOf<String>()
    var success = false

    // PharmScan valdation record is 82 bytes including CR/LF. It has all 0's except the MOT Id and 9th byte from last byte has the letter V 0x56.
    // Host system will look for this V in the last record to verify that the file is compete with all records sent across network.
    // Each row below is ten bytes. If host does not find V in last record of file it will ignore the *.new file.
    val validationRec = byteArrayOf(
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x4D,0x4F,0x54,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
        0x30,0x56,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,
//        0x0D,0x0A
    )

    val valRec = String(validationRec)

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
                    writer.println(valRec)
                    writer.println("END OF FILE")
                    success = true
                    soc.close()
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
