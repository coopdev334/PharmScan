package com.example.pharmscan.Repository

import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Data.Tables.HostCompName

class PharmScanRepo(
    private val db: PharmScanDb
) {
    // HostCompName db SQL
    suspend fun insertHostCompName(hostCompName: HostCompName) = db.getHostCompNameDaoDao().insert(hostCompName)
    suspend fun deleteHostCompName(hostCompName: HostCompName) = db.getHostCompNameDaoDao().delete(hostCompName)
    fun getAllHostCompName() = db.getHostCompNameDaoDao().getAll()
}