package com.example.pharmscan.Repository

import android.util.Log
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.SettingsDao
import com.example.pharmscan.Data.DAO.SystemInfoDao
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime


suspend fun repoUploadCollectedData(daoCollectedData: CollectedDataDao, daoSystemInfo: SystemInfoDao, daoSettings: SettingsDao) {

    // Get all collected data from table and write to ascii text file
    val collectedData = daoCollectedData.getAll()

    var out : BufferedWriter
    val secs = (LocalDateTime.now().second + (LocalDateTime.now().minute*60) + (LocalDateTime.now().hour*3600))
    val date = LocalDate.now()

    if (collectedData.isNotEmpty()) {
        try {

            var fileWriter =
                FileWriter("/sdcard/download/pharmscan_999_${date}_${secs.toString()}_789.new")

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
                            + row.seconds?.padStart(6, '0')
                            + row.itemtyp?.padStart(1, '0')
                            + row.itemcst?.padStart(8, '0'))
                )
                out.write("\n")
            }

            out.close()

            // Collected Data File (*.new) successfully created
            // Send all collected data *.new files to host server that
            // are found in download folder /sdcard/Download/
            val sysInfo = daoSystemInfo.getRow()
            val setting = daoSettings.getRow()
            // File sucessfully written, delete all records in collected data table
            // NOTE: delete all records from collected data table whether or not network
            // transmission was successful since we successfully created the *.new file with all
            // the collected data tables records added to file.
            daoCollectedData.deleteAll()
            // Update record count
            sysInfo[0].TotRecCount = "0"
            daoSystemInfo.insert(sysInfo[0])

            SendCollectedDataFileToHost(sysInfo[0].hostIpAddress!!, setting[0].hostServerPort!!)


        } catch (e: FileNotFoundException) {
            Log.d("coop", "FileNotFoundException")
            Log.d("coop", e.message!!)
        } catch (e: IOException) {
            Log.d("coop", "IOException")
            Log.d("coop", e.message!!)
        }
    }else {
        Log.d("coop", "Collected Data Table is empty. No data to send")
    }



}