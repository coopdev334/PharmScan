package com.example.pharmscan.ViewModel

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Environment
import android.util.Log
import androidx.navigation.NavController
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.runBlocking
import java.io.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun NdcSearch(navController: NavController, ndc: String, pharmScanViewModel:PharmScanViewModel) {
    // First formula - replace leading digit with a 0 then take remaining digits NOT including trailing check digit
    val ndcFirst = "0" + ndc.substring(1..10)
    var result = pharmScanViewModel.getNdcPSNdc(ndcFirst)

    if (result.isNullOrEmpty()) {
        // Apply Second formula - Take position 1 - 5
        val ndcSecond = ndc.substring(1..5) + "0" + ndc.substring(6..10)
        result = pharmScanViewModel.getNdcPSNdc(ndcSecond)
        if (result.isNullOrEmpty()) {
            // Apply Third formula - Take position 0 - 10 no formula applied just take first 11 digits
            val ndcThird = ndc.substring(0..10)
            result = pharmScanViewModel.getNdcPSNdc(ndcThird)
            if (result.isNullOrEmpty()) {
                val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000)
                navController.navigate(Screen.NdcNoMatchScreen.route)
            }else {
                val toneGG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
                toneGG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
                navController.navigate(Screen.NdcMatchScreen.withArgs(result[0].ndc!!, result[0].price!!, result[0].packsz!!))
            }
        }else{
            val toneGG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneGG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
            navController.navigate(Screen.NdcMatchScreen.withArgs(result[0].ndc!!, result[0].price!!, result[0].packsz!!))
        }
    }else{
        val toneGG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        toneGG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        navController.navigate(Screen.NdcMatchScreen.withArgs(result[0].ndc!!, result[0].price!!, result[0].packsz!!))
    }
}


// Insert new record into collected data table
fun InsertNdc(pharmScanViewModel:PharmScanViewModel, ndc: String, price: String, pksz: String, qty: String, matchFlg: String) {
    val sysinfo = pharmScanViewModel.getSystemInfoRow()
    var recnt: Int? = 1
    val collectedDataLastRow = pharmScanViewModel.getColDataLastInsertedRow()  // get last collected data row
    if (!collectedDataLastRow.isNullOrEmpty()) {
        recnt = collectedDataLastRow[0].recount?.toInt()?.plus(1)         // add 1 to record count
    }

    val secs = (LocalDateTime.now().second + (LocalDateTime.now().minute*60) + (LocalDateTime.now().hour*3600))
    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MM-dd-yy")
    val dateFormated = date.format(formatter)

    // Build Collected Data Table record with values
    val collectedData = CollectedData("000", "0000MOT", ndc, qty, price, pksz, "000", matchFlg, sysinfo[0].Tag, sysinfo[0].opid, recnt.toString(), dateFormated, secs.toString(), "P", "00000000")
    runBlocking {
        val job = pharmScanViewModel.insertCollectedData(collectedData)
        job.join()  // wait for insert to complete
    }

    val sysInfoMap = mapOf("TotRecCount" to recnt.toString())
    UpdateSystemInfo(pharmScanViewModel, sysInfoMap)
}


fun ProcessHoldState(qty: String, pharmScanViewModel:PharmScanViewModel) {
    var recnt: Int? = 1
    val collectedDataLastRow = pharmScanViewModel.getColDataLastInsertedRow()  // get last collected data row
    if (!collectedDataLastRow.isNullOrEmpty()) {
        recnt = collectedDataLastRow[0].recount?.toInt()?.plus(1)         // add 1 to record count
    }

    // Build Collected Data Table record with values
    val newCollectedData = CollectedData(collectedDataLastRow[0].dept, collectedDataLastRow[0].prodcd, collectedDataLastRow[0].ndc, qty, collectedDataLastRow[0].price, collectedDataLastRow[0].packsz, collectedDataLastRow[0].xstock, collectedDataLastRow[0].matchflg, collectedDataLastRow[0].loc, collectedDataLastRow[0].operid, recnt.toString(), collectedDataLastRow[0].date, collectedDataLastRow[0].seconds, collectedDataLastRow[0].itemtyp, collectedDataLastRow[0].itemcst)
    runBlocking {
        val job = pharmScanViewModel.insertCollectedData(newCollectedData)
        job.join()
    }

    val columnMap = mapOf("TotRecCount" to recnt.toString())
    UpdateSystemInfo(pharmScanViewModel, columnMap)
}



