package com.example.pharmscan.ui.Utility

import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.ViewModel.PharmScanViewModel
import kotlinx.coroutines.runBlocking

// update column values in Settings table. ONLY 1 row is allowed.
// Delete the existing 1 row and then insert 1 new row with
// updated column values
fun UpdateSettings(pharmScanViewModel: PharmScanViewModel, settings: Settings) {
    var settingsRow = pharmScanViewModel.getSettingsRow()  // get 1 row for delete

    runBlocking {
        // Check if no row inserted yet
        if (settingsRow.isNotEmpty()) {
            val job = pharmScanViewModel.deleteSettings(settingsRow[0])
            job.join() // wait for delete to complete
        }
    }

    pharmScanViewModel.insertSettings(settings)
}
