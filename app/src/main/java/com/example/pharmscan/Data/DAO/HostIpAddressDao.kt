package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmscan.Data.Tables.HostIpAddress


// This object contains all the SQL needed to access the
// database tables.
@Dao
interface HostIpAddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hostIpAddress: HostIpAddress)

    @Delete
    suspend fun deleteRow(hostIpAddress: HostIpAddress)

    @Query("DELETE FROM HostIpAddress")
    suspend fun deleteAll()

    @Query("SELECT * FROM HostIpAddress")
    fun getAllLiveData(): LiveData<List<HostIpAddress>>

    @Query("SELECT * FROM HostIpAddress")
    fun getAll(): List<HostIpAddress>
}