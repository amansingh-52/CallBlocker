package com.example.callblocker.utils

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.example.callblocker.R


const val NOTIFICATION_CHANNEL = "notification"

open class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("ApplicationCB", "Called")
            createNotificationsChannel(context = this.applicationContext)
        }
    }

    private fun createNotificationsChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CallNotifications"
            val descriptionText = "NewUpdates"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance)
            channel.description = descriptionText
            configureAndFireNotificationChannel(context, channel)
        }
    }

    private fun configureAndFireNotificationChannel(
        context: Context,
        channel: NotificationChannel
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.vibrationPattern = longArrayOf(500)
            channel.enableVibration(true)
            channel.lightColor = context.resources.getColor(R.color.primaryColor)
            channel.enableLights(true)
            channel.importance = NotificationManager.IMPORTANCE_HIGH
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            getNotificationManager(context).createNotificationChannel(channel)
        }
    }

    private fun getNotificationManager(context: Context): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }


}