package com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "bouquet_required_count",
    primaryKeys = ["bouquet_id", "flower_id"],
    foreignKeys = [
        ForeignKey(
            entity = BouquetEntity::class,
            parentColumns = ["id"],
            childColumns = ["bouquet_id"]
        ),
        ForeignKey(
            entity = FlowerEntity::class,
            parentColumns = ["id"],
            childColumns = ["flower_id"]
        )
    ],
    indices = [Index("flower_id")]
)
class BouquetFlowersRequiredEntity(
    @ColumnInfo("bouquet_id") val bouquetId: Int,
    @ColumnInfo("flower_id") val flowerId: Int,
    @ColumnInfo("required_flowers") val requiredFlowers: Int
)