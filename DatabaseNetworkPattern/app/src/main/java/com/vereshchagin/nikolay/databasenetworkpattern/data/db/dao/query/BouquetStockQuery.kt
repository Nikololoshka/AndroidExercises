package com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query

import androidx.room.Embedded
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetEntity

data class BouquetStockQuery(
    @Embedded val bouquet: BouquetEntity,
    val stock: Int,
)