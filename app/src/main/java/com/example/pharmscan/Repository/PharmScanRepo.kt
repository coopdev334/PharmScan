package com.example.pharmscan.Repository

import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.HostCompNameDao
import com.example.pharmscan.Data.DAO.PSNdcDao
import com.example.pharmscan.Data.DAO.SystemInfoDao
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.Data.Tables.SystemInfo

class PharmScanRepo(
    private val daoHostCompName: HostCompNameDao,
    private val daoCollectedData: CollectedDataDao,
    private val daoSystemInfo: SystemInfoDao,
    private val daoPSNdc: PSNdcDao
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

    // PSNdc db SQL
    suspend fun insertPSNdc(psNdc: PSNdc) = daoPSNdc.insert(psNdc)
    suspend fun deletePSNdc(psNdc: PSNdc) = daoPSNdc.delete(psNdc)
    fun getAllPSNdc() = daoPSNdc.getAll()
}