package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.Settings

// This object contains all the SQL needed to access the
// database tables.
@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Delete
    suspend fun delete(settings: Settings)

    @Query("SELECT * FROM Settings LIMIT 1")
    fun getAll(): List<Settings>

}