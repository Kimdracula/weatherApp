package com.homework.weatherapp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDB:RoomDatabase() {
    abstract fun historyDAO():HistoryDAO
}