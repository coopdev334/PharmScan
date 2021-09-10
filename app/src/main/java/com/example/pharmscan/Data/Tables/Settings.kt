package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "Settings")
data class Settings (
        var hostAcct: String?,
        var hostPassword: String?,
        var	ManualPrice: String?,
        var	CostLimit: String?,
        var	FileSendTagChgs: String?
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}