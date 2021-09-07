package com.example.pharmscan.Repository

import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.HostCompNameDao
import com.example.pharmscan.Data.DAO.SystemInfoDao
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Data.Tables.SystemInfo
import com.example.pharmscan.Data.DAO.SettingsDao
import com.example.pharmscan.Data.Tables.Settings

class PharmScanRepo(
    private val daoHostCompName: HostCompNameDao,
    private val daoCollectedData: CollectedDataDao,
    private val daoSystemInfo: SystemInfoDao,
    private val daoSettings: SettingsDao
) {
    // HostCompName db SQL
    suspend fun insertHostCompName(hostCompName: HostCompName) = daoHostCompName.insert(hostCompName)
    suspend fun deleteHostCompName(hostCompName: HostCompName) = daoHostCompName.delete(hostCompName)
    fun getAllHostCompName() = daoHostCompName.getAll()

    // CollectedData db SQL
    suspend fun insertCollectedData(collectedData: CollectedData) = daoCollectedData.insert(collectedData)
    suspend fun deleteCollectedData(collectedData: CollectedData) = daoCollectedData.delete(collectedData)
    fun getAllCollectedData() = daoCollectedData.getAll()
    fun getAllCollectedDataOrderByRecCnt() = daoCollectedData.getAllOrderByRecCnt()
    fun getAllCollectedDataOrderByTag() = daoCollectedData.getAllOrderByTag()
    fun getAllCollectedDataOrderByNdc() = daoCollectedData.getAllOrderByNdc()

    // SystemInfo db SQL
    suspend fun insertSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.insert(systemInfo)
    suspend fun deleteSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.delete(systemInfo)
    fun getAllSystemInfo() = daoSystemInfo.getAll()

    //Settings accessable SQL
    suspend fun insertSettings(settings: Settings) = daoSettings.insert(settings)
    suspend fun deleteSettings(settings: Settings) = daoSettings.delete(settings)
    fun getCurrentSettings() = daoSettings.getCurrent()
}