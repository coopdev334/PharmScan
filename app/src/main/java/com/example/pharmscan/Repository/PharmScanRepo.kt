package com.example.pharmscan.Repository

import com.example.pharmscan.Data.DAO.*
import com.example.pharmscan.Data.Tables.*

class PharmScanRepo(
    private val daoHostCompName: HostCompNameDao,
    private val daoCollectedData: CollectedDataDao,
    private val daoSystemInfo: SystemInfoDao,
    private val daoPSNdc: PSNdcDao,
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
    fun getColDataLastInsertedRow() = daoCollectedData.getLastInsertedRow()
    suspend fun uploadCollectedData() = repoUploadCollectedData()

    // SystemInfo db SQL
    suspend fun insertSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.insert(systemInfo)
    suspend fun deleteSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.delete(systemInfo)
    fun getAllSystemInfo() = daoSystemInfo.getAll()

    // PSNdc db SQL
    suspend fun insertPSNdc(psNdc: PSNdc) = daoPSNdc.insert(psNdc)
    suspend fun deletePSNdc(psNdc: PSNdc) = daoPSNdc.delete(psNdc)
    fun getAllPSNdc() = daoPSNdc.getAll()

    // Settings db SQL
    suspend fun insertSettings(settings: Settings) = daoSettings.insert(settings)
    suspend fun deleteSettings(settings: Settings) = daoSettings.delete(settings)
    fun getSettingsRow() = daoSettings.getRow()
}