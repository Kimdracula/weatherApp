package com.homework.weatherapp

import android.app.Application
import androidx.room.Room
import com.homework.weatherapp.domain.room.HistoryDAO
import com.homework.weatherapp.domain.room.HistoryDB

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }


    companion object {
        private var appContext: App? = null
        private var db: HistoryDB? = null
        fun getHistoryDAO(): HistoryDAO {
            if (db == null) {
                db = appContext?.let {
                    Room.databaseBuilder(
                        it,
                        HistoryDB::class.java, "database-history"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.historyDAO()

        }
    }
}