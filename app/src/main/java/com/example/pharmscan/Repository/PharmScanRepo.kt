package com.example.pharmscan.Repository

import com.example.pharmscan.Data.DAO.*
import com.example.pharmscan.Data.Tables.*

class PharmScanRepo(
    private val daoHostIpAddress: HostIpAddressDao,
    private val daoCollectedData: CollectedDataDao,
    private val daoSystemInfo: SystemInfoDao,
    private val daoPSNdc: PSNdcDao,
    private val daoSettings: SettingsDao
) {
    // HostIpAddress db SQL
    suspend fun insertHostIpAddress(hostIpAddress: HostIpAddress) = daoHostIpAddress.insert(hostIpAddress)
    suspend fun deleteRowHostIpAddress(hostIpAddress: HostIpAddress) = daoHostIpAddress.deleteRow(hostIpAddress)
    suspend fun deleteAllHostIpAddress() = daoHostIpAddress.deleteAll()
    fun getAllLiveDataHostIpAddress() = daoHostIpAddress.getAllLiveData()
    fun getAllHostIpAddress() = daoHostIpAddress.getAll()

    // CollectedData db SQL
    suspend fun insertCollectedData(collectedData: CollectedData) = daoCollectedData.insert(collectedData)
    suspend fun deleteCollectedDataRow(collectedData: CollectedData) = daoCollectedData.deleteRow(collectedData)
    suspend fun deleteAllCollectedData() = daoCollectedData.deleteAll()
    fun getAllLiveDataCollectedData() = daoCollectedData.getAllLiveData()
    fun getAllCollectedData() = daoCollectedData.getAll()
    fun getAllCollectedDataOrderByRecCnt() = daoCollectedData.getAllOrderByRecCnt()
    fun getAllCollectedDataOrderByTag() = daoCollectedData.getAllOrderByTag()
    fun getAllCollectedDataOrderByNdc() = daoCollectedData.getAllOrderByNdc()
    fun getColDataLastInsertedRow() = daoCollectedData.getLastInsertedRow()
    suspend fun uploadCollectedData() = repoUploadCollectedData(daoCollectedData, daoSystemInfo, daoSettings)
    fun getColDataQtyPriceByTag(tag: String) = daoCollectedData.getQtyPriceByTag(tag)

    // SystemInfo db SQL
    suspend fun insertSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.insert(systemInfo)
    suspend fun deleteRowSystemInfo(systemInfo: SystemInfo) = daoSystemInfo.deleteRow(systemInfo)
    suspend fun deleteAllSystemInfo() = daoSystemInfo.deleteAll()
    fun getLiveDataSystemInfoRow() = daoSystemInfo.getLiveDataRow()
    fun getSystemInfoRow() = daoSystemInfo.getRow()

    // PSNdc db SQL
    suspend fun insertPSNdc(psNdc: PSNdc) = daoPSNdc.insert(psNdc)
    suspend fun deleteRowPSNdc(psNdc: PSNdc) = daoPSNdc.deleteRow(psNdc)
    suspend fun deleteAllPSNdc() = daoPSNdc.deleteAll()
    fun getAllLiveDataPSNdc() = daoPSNdc.getAllLiveData()
    fun getAllPSNdc() = daoPSNdc.getAll()
    fun getNdcPSNdc(ndc: String) = daoPSNdc.getNdc(ndc)
    fun getAllPSNdcOrderByNdc() = daoPSNdc.getAllOrderByNdc()
    fun getAllPSNdcOrderByPrice() = daoPSNdc.getAllOrderByPrice()
    fun getAllPSNdcOrderByPackSz() = daoPSNdc.getAllOrderByPackSz()

    // Settings db SQL
    suspend fun insertSettings(settings: Settings) = daoSettings.insert(settings)
    suspend fun deleteRowSettings(settings: Settings) = daoSettings.deleteRow(settings)
    suspend fun deleteAllSettings() = daoSettings.deleteAll()
    fun getLiveDataSettingsRow() = daoSettings.getLiveDataRow()
    fun getSettingsRow() = daoSettings.getRow()
}