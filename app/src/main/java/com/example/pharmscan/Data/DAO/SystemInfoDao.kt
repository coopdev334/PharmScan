package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.SystemInfo

@Dao
interface SystemInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(systemInfo: SystemInfo)

    @Delete
    suspend fun delete(systemInfo: SystemInfo)

    @Query("SELECT * FROM SystemInfo")
    fun getAll(): List<SystemInfo>
}