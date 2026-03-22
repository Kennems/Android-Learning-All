package com.show.carhome.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.show.carhome.entity.CarBrandEntity

@Database(
    entities = [CarBrandEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carBrandDao(): CarBrandDao
}