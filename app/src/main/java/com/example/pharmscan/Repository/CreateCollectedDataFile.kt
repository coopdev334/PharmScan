package com.example.pharmscan.Repository

import android.util.Log
import androidx.navigation.NavController
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.PharmScanApplication
import com.example.pharmscan.R
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.SystemMsg
import java.io.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

suspend fun repoCreateCollectedDataFile(daoCollectedData: CollectedDataDao): Boolean {
    var success = false

    // Get all collected data from table and write to ascii text file
    val collectedData = daoCollectedData.getAll()

    var out : BufferedWriter
    val secs = (LocalDateTime.now().second + (LocalDateTime.now().minute*60) + (LocalDateTime.now().hour*3600))
    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yy")
    val dateFormated = date.format(formatter)

    if (collectedData.isNotEmpty()) {
        try {
            val filePath = PharmScanApplication.context?.getString(R.string.collected_data_file_path)
            var fileWriter =
                FileWriter("${filePath}pharmscan_999_${dateFormated}_${secs.toString()}_789.new")

            out = BufferedWriter(fileWriter);

            for (row in collectedData) {
                out.write(
                    row.dept?.padStart(3, '0')
                            + row.prodcd?.padStart(7, '0')
                            + row.ndc?.padStart(11, '0')
                            + (row.qty?.filterNot { it == "."[0] }?.padStart(6, '0')
                            + row.price?.filterNot { it == "."[0] }?.padStart(8, '0')
                            + row.packsz?.padStart(8, '0')
                            + row.xstock?.padStart(3, '0')
                            + row.matchflg?.padStart(1, '0')
                            + row.loc?.padStart(4, '0')
                            + row.operid?.padStart(3, '0')
                            + row.recount?.padStart(4, '0')
                            + row.date?.padStart(8, '0')
                            + row.seconds?.padStart(5, '0')
                            + row.itemtyp?.padStart(1, '0')
                            + row.itemcst?.padStart(8, '0'))
                )
                out.write("\n")
            }

            out.close()
            success = true

        } catch (e: FileNotFoundException) {
            Log.d("coop", "FileNotFoundException")
            Log.d("coop", e.message!!)
            SystemMsg("NOFILEFOUND", e.message!!)
        } catch (e: IOException) {
            Log.d("coop", "IOException")
            Log.d("coop", e.message!!)
            SystemMsg("FILEIOEXCEPTION", e.message!!)
        }
    }else {
        // Check for any already existing collected data files in download folder
        // If found return true to send those even though collected data table is empty
        val requiredSuffixes = listOf("new", "NEW")

        fun hasRequiredSuffix(file: File): Boolean {
            return requiredSuffixes.contains(file.extension)
        }

        val filePath = PharmScanApplication.context?.getString(R.string.collected_data_file_path)
        val cnt = File(filePath!!).walkTopDown().count { file ->
            file.isFile && hasRequiredSuffix(file)
        }

        // Found collected data files, return true to upload
        if (cnt > 0) {
            success = true
        } else {
            success = false
            Log.d("coop", "Collected Data Table is empty. No data to send")
            SystemMsg("COLLDATAEMPTY","No Data")
        }
    }

    return success
}