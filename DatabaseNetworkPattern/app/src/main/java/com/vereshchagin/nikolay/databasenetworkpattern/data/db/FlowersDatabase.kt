package com.vereshchagin.nikolay.databasenetworkpattern.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.BouquetDao
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.dao.FlowersDao
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetEntity
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.BouquetFlowersRequiredEntity
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.entity.FlowerEntity
import com.vereshchagin.nikolay.databasenetworkpattern.domain.model.FlowerType
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


@Database(
    entities = [
        BouquetEntity::class,
        FlowerEntity::class,
        BouquetFlowersRequiredEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class FlowersDatabase : RoomDatabase() {

    abstract fun flowersDao(): FlowersDao

    abstract fun bouquetDao(): BouquetDao

    companion object {

        @Volatile
        private var INSTANCE: FlowersDatabase? = null

        fun getDatabase(context: Context): FlowersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context = context.applicationContext,
                        klass = FlowersDatabase::class.java,
                        name = "flowers_database"
                    )
                    .allowMainThreadQueries() // для тестов и инициализации
                    .addMigrations(migrationFrom1To2())
                    .build()
                INSTANCE = instance

                // Initializing
                runBlocking {
                    instance
                        .flowersDao()
                        .insertFlowers(
                            FlowerType.entries.map {
                                FlowerEntity(
                                    id = it.uniqueId,
                                    name = it.name,
                                    description = it.name,
                                    availableCount = Random.nextInt(50,100)
                                )
                            }
                        )

                    instance
                        .bouquetDao()
                        .insertBouquets(
                            (1..10).map {
                                BouquetEntity(
                                    id = it,
                                    name = "Bouquet $it",
                                    description = "Bouquet $it desc"
                                )
                            }
                        )

                    instance
                        .bouquetDao()
                        .insertBouquetFlowers(
                            (1..10).flatMap { b ->
                                MutableList(Random.nextInt(2,5)) {
                                    BouquetFlowersRequiredEntity(
                                        bouquetId = b,
                                        flowerId = FlowerType.entries.random().uniqueId,
                                        requiredFlowers = Random.nextInt(2, 10)
                                    )
                                }
                            }
                        )
                }

                instance
            }
        }
    }
}