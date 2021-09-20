package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "SystemInfo")
data class SystemInfo(
    var opid: String? = null,
    var HHDeviceId: String? = null,
    var TotQty: String? = null,
    var TotAmt: String? = null,
    var TotRecCount: String? = null,
    var Tag: String? = null,
    var TagChangeCount: String? = null,
    var NdcLoading: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}
