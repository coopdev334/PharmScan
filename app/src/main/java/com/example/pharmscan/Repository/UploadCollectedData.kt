package com.example.pharmscan.Repository

import android.util.Log
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.SettingsDao
import com.example.pharmscan.Data.DAO.SystemInfoDao

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
        // Update record count
        if (sysInfo.isNotEmpty() && setting.isNotEmpty()) {
            repoSendCollectedDataFileToHost(sysInfo[0].hostIpAddress!!, setting[0].hostServerPort!!)
        }else {
            Log.d("coop", "sysInfo/setting empty")
        }
    }

}