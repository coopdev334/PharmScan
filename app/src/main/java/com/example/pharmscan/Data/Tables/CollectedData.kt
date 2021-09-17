package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table class containing the table name and column
// names. The primary key is used by Room to access
// the rows for SQL operations defined in the DAO.
@Entity(tableName = "CollectedData")
data class CollectedData (
    val	dept: String?,
    val	prodcd: String?,
    var	ndc: String?,
    var	qty: String?,
    var	price: String?,
    var	packsz: String?,
    val	xstock: String?,
    val	matchflg: String?,
    val	loc: String?,
    val	operid: String?,
    var	recount: String?,
    val	date: String?,
    val	seconds: String?,
    val	itemtyp: String?,
    var	itemcst: String?
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}