package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmscan.Data.Tables.PSNdc

@Dao
interface PSNdcDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(psNdc: PSNdc)

    @Delete
    suspend fun deleteRow(psNdc: PSNdc)

    @Query("DELETE FROM PSNdc")
    suspend fun deleteAll()

    @Query("SELECT * FROM PSNdc")
    fun getLiveDataRow(): LiveData<List<PSNdc>>

    @Query("SELECT * FROM PSNdc")
    fun getRow(): List<PSNdc>

}