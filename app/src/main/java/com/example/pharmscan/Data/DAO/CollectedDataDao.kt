package com.example.pharmscan.Data.DAO

import androidx.room.*
import com.example.pharmscan.Data.Tables.CollectedData
import com.example.pharmscan.Data.Tables.HostCompName
import com.example.pharmscan.ui.Navigation.CollectedDataViewCancelSearch

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

    @Query("SELECT ndc, qty, price, packsz, matchflg, loc, recount FROM CollectedData ORDER BY recount")
    fun getAllOrderByRecCnt(): MutableList<CollectedDataViewCancelSearch>

    @Query("SELECT ndc, qty, price, packsz, matchflg, loc, recount FROM CollectedData ORDER BY loc")
    fun getAllOrderByTag(): MutableList<CollectedDataViewCancelSearch>

    @Query("SELECT ndc, qty, price, packsz, matchflg, loc, recount FROM CollectedData ORDER BY ndc")
    fun getAllOrderByNdc(): MutableList<CollectedDataViewCancelSearch>


}