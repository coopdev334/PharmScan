package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "Settings")
data class Settings(
    var HostName: String? = null,
    var HostPassword: String? = null,
    var NumTagChanges: String? = null,
    var CostLimit: String? = null,
    var PriceEntry: Boolean? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var staticID: Int? = null
}
