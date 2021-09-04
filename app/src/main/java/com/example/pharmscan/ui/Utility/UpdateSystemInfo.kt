package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel

// update column values in SystemInfo table. ONLY 1 row is allowed.
// Save current SystemInfo data, then delete the 1 row and then insert new row with
// update column values
fun UpdateSystemInfo(pharmScanViewModel: PharmScanViewModel, columnValue: Map<String, String>) {
    var systemInfo = pharmScanViewModel.getAllSystemInfo()

    if (systemInfo.isEmpty()) {
        // set to defaults then update columns
        systemInfo = listOf(SystemInfo("0", "0", "0", "0", "0"))
    }else {
        pharmScanViewModel.deleteSystemInfo(systemInfo[0])
    }

    for (item in columnValue) {
        when (item.key) {
            "opid" -> systemInfo[0].opid = item.value
            "HHDevice" -> systemInfo[0].HHDeviceId = item.value
            "TotQty" -> systemInfo[0].TotQty = item.value
            "TotAmt" -> systemInfo[0].TotAmt = item.value
            "TotRecCount" -> systemInfo[0].TotRecCount = item.value
        }
    }

    pharmScanViewModel.insertSystemInfo(systemInfo[0])
}