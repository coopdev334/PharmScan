package com.example.pharmscan.Data.Tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PSNdc")
data class PSNdc(
    val ndc: String?,
    val price: String?,
    val packsz: String?
) {
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null
}