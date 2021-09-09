package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

// update column values in SystemInfo table. ONLY 1 row is allowed.
// Save current SystemInfo data, then delete the 1 row and then insert new row with
// update column values
fun UpdateSystemInfo(pharmScanViewModel: PharmScanViewModel, columnValue: Map<String, String>) {
    var systemInfo = pharmScanViewModel.getAllSystemInfo()

    runBlocking {

        if (systemInfo.isEmpty()) {
            // set to defaults then update columns
            systemInfo = listOf(SystemInfo("0", "0", "0", "0", "0", "0"))
        } else {
            val job = pharmScanViewModel.deleteSystemInfo(systemInfo[0])
            job.join() // wait for delete to complete
        }
    }

    for (item in columnValue) {
        when (item.key) {
            "opid" -> systemInfo[0].opid = item.value
            "HHDevice" -> systemInfo[0].HHDeviceId = item.value
            "TotQty" -> systemInfo[0].TotQty = item.value
            "TotAmt" -> systemInfo[0].TotAmt = item.value
            "TotRecCount" -> systemInfo[0].TotRecCount = item.value
            "Tag" -> systemInfo[0].Tag = item.value
        }
    }

    pharmScanViewModel.insertSystemInfo(systemInfo[0])
}
