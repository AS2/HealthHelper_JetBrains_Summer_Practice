package com.example.user.healthhelper

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class MyService : Service() {

    private var isMakedRecomendations : Boolean = true

    override fun onCreate() {
        super.onCreate()
        Log.d("demo", "onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("demo", "onStartCommand")
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        val sdf = SimpleDateFormat("HH:mm")
        val currentTime = sdf.format(Date())
        val hoursAndMinutes = currentTime.split(":")

        val recact = RecomendationsActivity()

        Log.d("demo", "onBind")

        if ((hoursAndMinutes[0].toInt() * 60 + hoursAndMinutes[1].toInt() >= 1410) && isMakedRecomendations) {
            recact.MakeRecomendations()
            Log.d("demo", "${hoursAndMinutes[0].toInt() * 60 + hoursAndMinutes[1].toInt()} - MAKE SUGGESTIONS;")
            isMakedRecomendations = false
        }

        if (hoursAndMinutes[0].toInt() * 60 + hoursAndMinutes[1].toInt() < 1410)
            Log.d("demo", "${hoursAndMinutes[0].toInt() * 60 + hoursAndMinutes[1].toInt()} - WE CAN MAKE SUGGESTIONS;")
            isMakedRecomendations = true

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("demo", "onDestroy")
    }

}