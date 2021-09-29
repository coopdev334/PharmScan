package com.example.pharmscan.Data.DAO

import androidx.lifecycle.LiveData
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
    suspend fun deleteRow(collectedData: CollectedData)

    @Query("DELETE FROM CollectedData")
    suspend fun deleteAll()

    @Query("SELECT * FROM CollectedData")
    fun getAllLiveData(): LiveData<List<CollectedData>>

    @Query("SELECT * FROM CollectedData")
    fun getAll(): List<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY CAST(recount as unsigned)")
    fun getAllOrderByRecCnt(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY CAST(loc as unsigned)")
    fun getAllOrderByTag(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData ORDER BY CAST(ndc as unsigned)")
    fun getAllOrderByNdc(): MutableList<CollectedData>

    @Query("SELECT * FROM CollectedData WHERE iD = (SELECT MAX(iD) FROM CollectedData) LIMIT 1")
    fun getLastInsertedRow(): List<CollectedData>

    @Query("SELECT qty, price FROM CollectedData WHERE :tag = loc")
    fun getQtyPriceByTag(tag: String): List<CollectedData>


}
