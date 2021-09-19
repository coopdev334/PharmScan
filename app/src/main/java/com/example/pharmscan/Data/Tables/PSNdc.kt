package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PSNdc")
data class PSNdc(
    var ndc: String?,
    var price: String?,
    var packsz: String?
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}