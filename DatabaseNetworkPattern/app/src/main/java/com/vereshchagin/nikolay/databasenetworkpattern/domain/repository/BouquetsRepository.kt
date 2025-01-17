package com.vereshchagin.nikolay.databasenetworkpattern.domain.repository

import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.BouquetStock
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.BouquetWithFlower
import kotlinx.coroutines.flow.Flow

interface BouquetsRepository {

    fun getAvailableBouquets(): Flow<List<BouquetStock>>

    suspend fun buyBouquet(bouquetId: Int)
}