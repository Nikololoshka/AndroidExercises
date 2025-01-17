package com.vereshchagin.nikolay.databasenetworkpattern.data.mapper

import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query.BouquetStockQuery
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query.BouquetWithFlowersQuery
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.FlowerEntity
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.BouquetStock
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.BouquetWithFlower
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.Flower
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.FlowerCount
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.FlowerType

fun BouquetStockQuery.toBouquetStock(): BouquetStock {
    return BouquetStock(
        id = bouquet.id,
        name = bouquet.name,
        description = bouquet.description,
        stock = stock
    )
}

fun BouquetWithFlowersQuery.toBouquet(): BouquetWithFlower {
    return BouquetWithFlower(
        id = bouquet.id,
        name = bouquet.name,
        flowers = flowers.zip(flowersCount) { flawer, count ->
            FlowerCount(
                flower = flawer.toFlower(),
                count = count
            )
        },
        description = bouquet.description
    )
}

fun FlowerEntity.toFlower(): Flower {
    return Flower(
        id = FlowerType.fromUniqueId(id),
        name = name,
        description = description
    )
}