package com.example.pharmscan.Repository

import com.example.pharmscan.Data.DAO.HostCompNameDao
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Data.Tables.HostCompName

class PharmScanRepo(
    private val dao: HostCompNameDao
) {
    // HostCompName db SQL
    suspend fun insert(hostCompName: HostCompName) = dao.insert(hostCompName)
    suspend fun delete(hostCompName: HostCompName) = dao.delete(hostCompName)
    fun getAll() = dao.getAll()
}