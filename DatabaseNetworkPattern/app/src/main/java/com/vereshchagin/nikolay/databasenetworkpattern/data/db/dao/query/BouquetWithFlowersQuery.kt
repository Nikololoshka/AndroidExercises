package com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetEntity
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetFlowersRequiredEntity
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.FlowerEntity


data class BouquetWithFlowersQuery(
    @Embedded val bouquet: BouquetEntity,
    @Relation(
        entity = FlowerEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = BouquetFlowersRequiredEntity::class,
            parentColumn = "bouquet_id",
            entityColumn = "flower_id"
        )
    )
    val flowers: List<FlowerEntity>,
    @Relation(
        entity = BouquetFlowersRequiredEntity::class,
        parentColumn = "id",
        entityColumn = "bouquet_id",
        projection = ["required_flowers"]
    )
    val flowersCount: List<Int>
)