package com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bouquets")
data class BouquetEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val decoration: Int = 0 // migrate field
)