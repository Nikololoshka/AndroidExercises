package com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class FlowerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo("available_count") val availableCount: Int,
    val production: String? = null // migrate field
)