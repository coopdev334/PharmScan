package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

// update individual column values in Settings table. ONLY 1 row is allowed.
// Delete the existing 1 row and then insert 1 new row with
// updated column values
fun UpdateSettings(pharmScanViewModel: PharmScanViewModel, columnValue: Map<String, String>? = null) {
    var settingsRow = pharmScanViewModel.getSettingsRow()

    runBlocking {

        //if (settingsRow.isNullOrEmpty() || columnValue.isNullOrEmpty()) {
        if (settingsRow.isNullOrEmpty()) {
            // set to defaults then update columns
            settingsRow = listOf(Settings("None", "None", "off", "0.00", "0", "on"))
        } else {
            val job = pharmScanViewModel.deleteAllSettings()
            job.join() // wait for deleteRow to complete
        }
    }

    if (!columnValue.isNullOrEmpty()) {
        for (item in columnValue) {
            when (item.key) {
                "hostAcct" -> settingsRow[0].hostAcct = item.value
                "hostPassword" -> settingsRow[0].hostPassword = item.value
                "ManualPrice" -> settingsRow[0].ManualPrice = item.value
                "CostLimit" -> settingsRow[0].CostLimit = item.value
                "FileSendTagChgs" -> settingsRow[0].FileSendTagChgs = item.value
                "AutoLoadNdcFile" -> settingsRow[0].AutoLoadNdcFile = item.value
            }
        }
    }

    pharmScanViewModel.insertSettings(settingsRow[0])

}

// update all column values in Settings table at once. ONLY 1 row is allowed.
// Delete the existing 1 row and then insert 1 new row with
// updated column values
fun UpdateSettings(pharmScanViewModel: PharmScanViewModel, settings: Settings) {
    val settingsRow = pharmScanViewModel.getSettingsRow()

    runBlocking {
        // Check if no row inserted yet
        if (settingsRow.isNotEmpty()) {
            val job = pharmScanViewModel.deleteAllSettings()
            job.join() // wait for deleteRow to complete
        }
    }

    pharmScanViewModel.insertSettings(settings)
}
