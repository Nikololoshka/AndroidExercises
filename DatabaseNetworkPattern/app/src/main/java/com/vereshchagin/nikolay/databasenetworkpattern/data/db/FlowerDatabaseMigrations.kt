package com.vereshchagin.nikolay.databasenetworkpattern.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Провести миграцию БД из первого задания,
 * используя класс Migration
 * - добавить к букету поле "оформление"(крафтовая бумага, корзина и тд),
 * - добавить к цветам поле "страна-производитель".
 */
fun migrationFrom1To2() = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE bouquets ADD COLUMN decoration INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE flowers  ADD COLUMN production TEXT NULL")
    }
}