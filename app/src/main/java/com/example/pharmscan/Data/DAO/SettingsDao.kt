package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pharmscan.Data.Tables.Settings

// This object contains all the SQL needed to access the
// database tables.
@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Delete
    suspend fun deleteRow(settings: Settings)

    @Query("DELETE FROM Settings")
    suspend fun deleteAll()

    @Query("SELECT * FROM Settings LIMIT 1")
    fun getLiveDataRow(): LiveData<List<Settings>>

    @Query("SELECT * FROM Settings LIMIT 1")
    fun getRow(): List<Settings>

}