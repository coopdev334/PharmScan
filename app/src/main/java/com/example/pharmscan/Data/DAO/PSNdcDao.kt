package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.Data.Tables.SystemInfo

@Dao
interface PSNdcDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(psNdc: PSNdc)

    @Delete
    suspend fun delete(psNdc: PSNdc)

    @Query("SELECT * FROM PSNdc")
    fun getAll(): List<PSNdc>
}