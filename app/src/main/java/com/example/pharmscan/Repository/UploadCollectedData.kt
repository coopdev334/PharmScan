package com.example.pharmscan.Repository

import android.util.Log
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.SettingsDao
import com.example.pharmscan.Data.DAO.SystemInfoDao
import com.example.pharmscan.ui.Utility.SystemMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

suspend fun repoUploadCollectedData(daoCollectedData: CollectedDataDao, daoSystemInfo: SystemInfoDao, daoSettings: SettingsDao) {

    if (repoCreateCollectedDataFile(daoCollectedData)) {
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

        if (sysInfo.isNotEmpty() && setting.isNotEmpty()) {
            // Update record count
            sysInfo[0].TotRecCount = "0"
            CoroutineScope(Dispatchers.IO).launch {
                daoSystemInfo.insert(sysInfo[0])
            }

            if (repoSendCollectedDataFileToHost(sysInfo[0].hostIpAddress!!, setting[0].hostServerPort!!, sendSysMsg = true)) {
                SystemMsg("CLOSESYSACTIVITY", "close activity")
            }
        }else {
            Log.d("coop", "sysInfo/setting empty")
        }
    }

}