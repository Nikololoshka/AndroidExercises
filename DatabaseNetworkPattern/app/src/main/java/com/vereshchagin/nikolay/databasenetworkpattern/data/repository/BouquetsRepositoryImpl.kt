package com.vereshchagin.nikolay.databasenetworkpattern.data.repository

import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.BouquetDao
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.FlowersDao
import com.vereshchagin.nikolay.databasenetworkpattern.data.mapper.toBouquetStock
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.BouquetStock
import com.vereshchagin.nikolay.databasenetworkpattern.domain.repository.BouquetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**

Есть цветочный магазин. Имеем 10 видов цветов, юзеру на фронте доступны готовые букеты,
собранные из цветов (цветов разного вида в букете может быть разное количество).

Пример: букет из 3 белых роз, 2 тюльпанов, 10 красных роз.

Спроектировать базу данных и реализовать в Android Studio.

Учесть кейсы:
- при первом запуске приложения получать данные о кол-ве доступных к покупке букетов;
- при покупке букета юзером убирать из БД купленные цветы.

(Писать само приложение с фронтом не нужно, достаточно написать в data слое DAO, создать таблицы)

 */

class BouquetsRepositoryImpl(
    private val bouquetDao: BouquetDao,
    private val flowersDao: FlowersDao
): BouquetsRepository {

    override fun getAvailableBouquets(): Flow<List<BouquetStock>> {
        return bouquetDao.getMaxBouquetsAvailable()
            .map { entities -> entities.map { it.toBouquetStock() } }
    }

    override suspend fun buyBouquet(bouquetId: Int) {
        bouquetDao.purchaseBouquet(flowersDao, bouquetId)
    }
}