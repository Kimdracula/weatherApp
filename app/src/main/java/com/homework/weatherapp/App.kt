package com.homework.weatherapp

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.homework.weatherapp.domain.room.Database
import com.homework.weatherapp.domain.room.HistoryDAO

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
                    ).build()
                }
            }
            return db!!.historyDAO()

        }
    }
}