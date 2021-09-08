package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.PSNdc

// This object contains all the SQL needed to access the
// database tables.
@Dao
interface CollectedDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectedData: CollectedData)

    @Delete
    suspend fun delete(collectedData: CollectedData)

    @Query("SELECT * FROM CollectedData")
    fun getAll(): List<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY recount")
    fun getAllOrderByRecCnt(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY loc")
    fun getAllOrderByTag(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY ndc")
    fun getAllOrderByNdc(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData WHERE iD = (SELECT MAX(iD) FROM CollectedData) LIMIT 1")
    fun getLastInsertedRow(): List<CollectedData>


}
