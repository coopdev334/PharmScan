package com.example.pharmscan.Repository

import android.util.Log
import android.widget.Toast
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.ui.Utility.ToastDisplay
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime


suspend fun repoUploadCollectedData(daoCollectedData: CollectedDataDao) {
ToastDisplay("Uploading Collected Data", Toast.LENGTH_LONG)
    // TODO: Implemenet actual send to host through network


    // Get all collected data from table and write to ascii text file
    val collectedData = daoCollectedData.getAll()

    var out : BufferedWriter
    val secs = (LocalDateTime.now().second + (LocalDateTime.now().minute*60) + (LocalDateTime.now().hour*3600))
    val date = LocalDate.now()
    try {

        var fileWriter = FileWriter("/sdcard/download/pharmscan_999_${date}_${secs.toString()}_789.new")

        out = BufferedWriter(fileWriter);

        for (row in collectedData){
            out.write(
                row.dept?.padStart(3, '0')
                + row.prodcd?.padStart(8, '0')
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

    }catch (e: FileNotFoundException) {
        Log.d("coop", "FileNotFoundException")
        Log.d("coop", e.message!!)
    }catch (e: IOException) {
        Log.d("coop", "IOException")
        Log.d("coop", e.message!!)
    }





}