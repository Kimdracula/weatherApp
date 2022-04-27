package com.homework.weatherapp.domain.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table",primaryKeys = ["city", "timestamp"])
data class HistoryEntity(
    var city: String,
    val temperature: Int,
    val fellsLike: Int,
    val condition: String,
    val humidity: Int,
    val icon: String,
    val timestamp: Long
)

