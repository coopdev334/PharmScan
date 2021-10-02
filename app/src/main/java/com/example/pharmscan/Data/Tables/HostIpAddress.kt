package com.example.pharmscan.Data.Tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "HostIpAddress")
data class HostIpAddress (
    val name: String?
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}
