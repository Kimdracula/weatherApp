package com.homework.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class SystemBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
    //    Toast.makeText(p0, "Смена параметров сетевого подключения", Toast.LENGTH_LONG).show()
        Log.d("!!!", p1.toString())
    }
}