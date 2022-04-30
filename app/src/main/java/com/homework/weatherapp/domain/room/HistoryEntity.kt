package com.homework.weatherapp.domain.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "history_table", primaryKeys = ["city", "createdAt"])
data class HistoryEntity(
    var city: String,
    val temperature: Int,
    val fellsLike: Int,
    val condition: String,
    val humidity: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Long
 //   val icon: String,
)

