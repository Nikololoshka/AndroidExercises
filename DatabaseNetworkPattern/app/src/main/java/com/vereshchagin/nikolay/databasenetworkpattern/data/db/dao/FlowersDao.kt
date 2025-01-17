package com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.FlowerEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FlowersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlowers(flowers: List<FlowerEntity>)

    @Query("SELECT * FROM flowers")
    fun getAllFlowers(): Flow<FlowerEntity>

    @Query("UPDATE flowers SET available_count = available_count - :count WHERE id = :id AND available_count >= :count")
    suspend fun decreaseFlowerStock(id: Int, count: Int): Int
}