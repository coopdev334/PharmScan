package com.example.pharmscan.ViewModel

import androidx.navigation.NavController
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.runBlocking

fun NdcSearch(navController: NavController, ndc: String, pharmScanViewModel:PharmScanViewModel) {
    val result = pharmScanViewModel.getNdcPSNdc(ndc)

    if (result.isNullOrEmpty()) {
        navController.navigate(Screen.NdcNoMatchScreen.route)
    }else{
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

    // Build Collected Data Table record with values
    // TODO: Find where itemcost field comes from. Temporary using price
    val collectedData = CollectedData("000", "12345678", ndc, qty, price, pksz, "123", matchFlg, sysinfo[0].Tag, sysinfo[0].opid, recnt.toString(), "12/12/21", "123456", "P", price)
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
    // TODO: Find where itemcost field comes from. Temporary using price
    val newCollectedData = CollectedData(collectedDataLastRow[0].dept, collectedDataLastRow[0].prodcd, collectedDataLastRow[0].ndc, qty, collectedDataLastRow[0].price, collectedDataLastRow[0].packsz, collectedDataLastRow[0].xstock, collectedDataLastRow[0].matchflg, collectedDataLastRow[0].loc, collectedDataLastRow[0].operid, recnt.toString(), collectedDataLastRow[0].date, collectedDataLastRow[0].seconds, collectedDataLastRow[0].itemtyp, collectedDataLastRow[0].itemcst)
    runBlocking {
        val job = pharmScanViewModel.insertCollectedData(newCollectedData)
        job.join()
    }

    val columnMap = mapOf("TotRecCount" to recnt.toString())
    UpdateSystemInfo(pharmScanViewModel, columnMap)
}
