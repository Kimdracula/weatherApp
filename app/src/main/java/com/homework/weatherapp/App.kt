package com.homework.weatherapp

import android.app.Application
import androidx.room.Room
import com.homework.weatherapp.domain.room.HistoryDAO
import com.homework.weatherapp.domain.room.Database

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }


    companion object {
        private var appContext: App? = null
        private var db: Database? = null
        fun getHistoryDAO(): HistoryDAO {
            if (db == null) {
                db = appContext?.let {
                    Room.databaseBuilder(
                        it,
                        Database::class.java, "database-history"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.historyDAO()

        }
    }
}