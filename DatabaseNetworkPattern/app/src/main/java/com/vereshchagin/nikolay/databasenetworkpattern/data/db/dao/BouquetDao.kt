package com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query.BouquetStockQuery
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.query.BouquetWithFlowersQuery
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetEntity
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetFlowersRequiredEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BouquetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBouquets(flowers: List<BouquetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBouquetFlowers(flowers: List<BouquetFlowersRequiredEntity>)

    @Transaction
    @Query(
        """
        SELECT b.*, MIN(f.available_count / bf.required_flowers) AS stock
            FROM bouquets AS b
            INNER JOIN bouquet_required_count AS bf ON b.id = bf.bouquet_id
            INNER JOIN flowers AS f ON bf.flower_id = f.id
            GROUP BY b.id
    """
    )
    fun getMaxBouquetsAvailable(): Flow<List<BouquetStockQuery>>

    /**
     * В качестве теста получаю средствами Room,
     * хотя лучше бы было как выше сделать
     */
    @Transaction
    @Query("SELECT * FROM bouquets WHERE id = :bouquetId")
    suspend fun getBouquetsWithFlowers(bouquetId: Int): BouquetWithFlowersQuery

    @Transaction
    @Query("SELECT * FROM bouquets")
    fun getAllBouquetsWithFlowers(): Flow<List<BouquetWithFlowersQuery>>

    @Query("SELECT * FROM bouquets")
    fun getAllBouquets(): Flow<BouquetEntity>

    @Transaction
    suspend fun purchaseBouquet(flowerDao: FlowersDao, bouquetId: Int) {
        val bouquet = getBouquetsWithFlowers(bouquetId)

        bouquet.flowers.zip(bouquet.flowersCount)
            .forEach { (flower, requiredFlowers) ->
                val decreasedCount = flowerDao.decreaseFlowerStock(flower.id, requiredFlowers)
                if (decreasedCount == 0) {
                    throw NoSuchElementException("Flowers ${flower.id} is out of stock")
                }
            }
    }
}