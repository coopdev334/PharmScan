package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.HostCompName

// This object contains all the SQL needed to access the
// database tables.
@Dao
interface HostCompNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hostCompName: HostCompName)

    @Delete
    suspend fun delete(hostCompName: HostCompName)

    @Query("SELECT * FROM HostCompName")

    fun getAll(): List<HostCompName>
}