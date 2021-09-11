package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.runBlocking

// update column values in SystemInfo table. ONLY 1 row is allowed.
// Save current SystemInfo data, then deleteRow the 1 row and then insert new row with
// update column values
fun UpdateSystemInfo(pharmScanViewModel: PharmScanViewModel, columnValue: Map<String, String>) {
    // Get the current SystemInfo values
    var systemInfo = pharmScanViewModel.getSystemInfoRow()

    runBlocking {

        if (systemInfo.isEmpty()) {
            // set to defaults then update columns
            systemInfo = listOf(SystemInfo("0", "0", "0", "0", "0", "0", "0"))
        } else {
            val job = pharmScanViewModel.deleteRowSystemInfo(systemInfo[0])
            job.join() // wait for deleteRow to complete
        }
    }

    for (item in columnValue) {
        when (item.key) {
            "opid" -> systemInfo[0].opid = item.value
            "HHDevice" -> systemInfo[0].HHDeviceId = item.value
            "TotQty" -> systemInfo[0].TotQty = item.value
            "TotAmt" -> systemInfo[0].TotAmt = item.value
            "TotRecCount" -> systemInfo[0].TotRecCount = item.value
            "Tag" -> {
                if (systemInfo[0].Tag != "0" && item.value != systemInfo[0].Tag){
                    val tagCnt = systemInfo[0].TagChangeCount?.toInt()
                    val cnt = tagCnt?.plus(1)
                    systemInfo[0].TagChangeCount = cnt.toString()
                }
                systemInfo[0].Tag = item.value

            }
        }
    }

    pharmScanViewModel.insertSystemInfo(systemInfo[0])
}
