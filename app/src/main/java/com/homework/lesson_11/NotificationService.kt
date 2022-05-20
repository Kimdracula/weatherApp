package com.homework.lesson_11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.homework.weatherapp.R

class NotificationService : FirebaseMessagingService() {

    // AAAAcOwwncU:APA91bHXFgMDtLK-30YMqmDmA-S784VTJ3EmRpIOuT0Bi8zekQuf1qtGavxHiz-IcctRCzyADUvfVproqwrli-wzmZ7uNzlPZpTRMY9TEMSLb7r5zNlOB66FWeK9B3Tmp7ZPaSQYOA7Q

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val remoteMessageData = remoteMessage.data
        if (remoteMessageData.isNotEmpty()) {
            val title = remoteMessageData[KEY_TITLE]
            val message = remoteMessageData[KEY_MESSAGE]
            if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
                pushNotification(title, message)
            }
        }
    }

    private fun pushNotification(title: String, message: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilderLow =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID_LOW).apply {
                setSmallIcon(R.drawable.ic_baseline_warning_24)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_LOW
            }

        val notificationBuilderHigh =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID_LOW).apply {
                setSmallIcon(R.drawable.ic_baseline_warning_24)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                        R.drawable.squirrel))
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_HIGH
            }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannelLow(notificationManager)
            createNotificationChannelHigh(notificationManager)

        }
        notificationManager.notify(NOTIFICATION_ID_LOW, notificationBuilderLow.build())
        notificationManager.notify(NOTIFICATION_ID_HIGH, notificationBuilderHigh.build())
    }

    private fun createNotificationChannelLow(
        notificationManager:
        NotificationManager
    ) {
        val name = "Low priority Channel"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID_LOW, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationChannelHigh(
        notificationManager:
        NotificationManager
    ) {
        val name = "High priority Channel"
        val descriptionText = "Channel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID_HIGH, name, importance).apply {
            description = descriptionText
            enableLights(true)
            lightColor = Color.RED
        }
        notificationManager.createNotificationChannel(channel)
    }


    override fun onNewToken(token: String) {
    }


}