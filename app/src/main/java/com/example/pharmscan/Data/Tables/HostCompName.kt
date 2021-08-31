package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "HostCompName")
data class HostCompName (
    val hostCompName: String?
) {
    @PrimaryKey(autoGenerate = true)
    private val id: Int? = null
}