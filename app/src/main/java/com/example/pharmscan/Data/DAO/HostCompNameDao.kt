package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.Data.Tables.Settings

// This object contains all the SQL needed to access the
// database tables.
@Dao
interface HostCompNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hostCompName: HostCompName)

    @Delete
    suspend fun deleteRow(hostCompName: HostCompName)

    @Query("DELETE FROM HostCompName")
    suspend fun deleteAll()

    @Query("SELECT * FROM HostCompName")
    fun getAllLiveData(): LiveData<List<HostCompName>>

    @Query("SELECT * FROM HostCompName")
    fun getAll(): List<HostCompName>
}