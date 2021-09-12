package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmscan.Data.Tables.Settings
import com.example.pharmscan.Data.Tables.SystemInfo

@Dao
interface SystemInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(systemInfo: SystemInfo)

    @Delete
    suspend fun deleteRow(systemInfo: SystemInfo)

    @Query("DELETE FROM Settings")
    suspend fun deleteAll()

    @Query("SELECT * FROM SystemInfo LIMIT 1")
    fun getLiveDataRow(): LiveData<List<SystemInfo>>

    @Query("SELECT * FROM SystemInfo LIMIT 1")
    fun getRow(): List<SystemInfo>
}