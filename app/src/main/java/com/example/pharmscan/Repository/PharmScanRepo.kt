package com.example.pharmscan.Repository

import android.media.CamcorderProfile.getAll
import com.example.pharmscan.Data.DAO.CollectedDataDao
import com.example.pharmscan.Data.DAO.HostCompNameDao
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.HostCompName

class PharmScanRepo(
    private val daoHostCompName: HostCompNameDao,
    private val daoCollectedData: CollectedDataDao
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
}