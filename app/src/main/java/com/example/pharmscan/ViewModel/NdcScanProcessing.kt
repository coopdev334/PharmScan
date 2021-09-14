package com.example.pharmscan.ViewModel

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.ui.Screen.Screen
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import kotlinx.coroutines.runBlocking

fun NdcSearch(navController: NavController, ndc: String, pharmScanViewModel:PharmScanViewModel) {
    val result = pharmScanViewModel.getNdcPSNdc(ndc)

    if (result.isNullOrEmpty()) {
        navController.navigate(Screen.NdcNoMatchScreen.route)
    }else{
        // Insert new record into PSNdc table
        // TODO: get qty from user and other data
        //val collectedData = CollectedData("111", "12345678", ndc, "123456", "12345678", "12345678", "123", "R", "1111", "123", "1001", "12/12/21", "123456", "P", "12345678")
        //pharmScanViewModel.insertCollectedData(collectedData)
        navController.navigate(Screen.NdcMatchScreen.withArgs(result[0].ndc!!, result[0].price!!, result[0].packsz!!))
    }
}


// Insert new record into collected data table
fun InsertNdc(navController: NavController, pharmScanViewModel:PharmScanViewModel, ndc: String, price: String, pksz: String, qty: String, matchFlg: String) {
    val sysinfo = pharmScanViewModel.getSystemInfoRow()
    val recnt = sysinfo[0].TotRecCount?.toInt()?.plus(1)
    val recMap = mapOf("TotRecCount" to recnt.toString())
    UpdateSystemInfo(pharmScanViewModel, recMap)

    // Build Collected Data Table record with values
    // TODO: Find where itemcost field comes from. Temporary using price
    val collectedData = CollectedData("000", "12345678", ndc, qty, price, pksz, "123", matchFlg, sysinfo[0].Tag, sysinfo[0].opid, recnt.toString(), "12/12/21", "123456", "P", price)
    pharmScanViewModel.insertCollectedData(collectedData)
}


fun ProcessHoldState(qty: String, pharmScanViewModel:PharmScanViewModel) {
    val sysinfo = pharmScanViewModel.getSystemInfoRow()
    var collectedData = pharmScanViewModel.getColDataLastInsertedRow()
    val recnt = sysinfo[0].TotRecCount?.toInt()?.plus(1)
    val recMap = mapOf("TotRecCount" to recnt.toString())
    UpdateSystemInfo(pharmScanViewModel, recMap)

    // Build Collected Data Table record with values
    // TODO: Find where itemcost field comes from. Temporary using price
    collectedData[0].recount = recnt.toString()
    collectedData[0].qty = qty
    pharmScanViewModel.insertCollectedData(collectedData[0])
}
