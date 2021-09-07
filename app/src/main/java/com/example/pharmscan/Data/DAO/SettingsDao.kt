package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.Settings


@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Delete
    suspend fun delete(settings: Settings)

    @Query("SELECT * FROM Settings LIMIT 1")
    fun getCurrent(): List<Settings>
}