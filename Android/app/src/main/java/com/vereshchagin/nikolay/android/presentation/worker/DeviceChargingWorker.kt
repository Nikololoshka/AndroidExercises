package com.vereshchagin.nikolay.android.presentation.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vereshchagin.nikolay.android.R


class DeviceChargingWorker(
    context: Context, params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, DEFAULT_CHANNEL)
            .setSmallIcon(R.drawable.ic_charging_station)
            .setContentTitle("Device is charging")
            .setContentText("Very important notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.notify(1000, notification)

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val defaultChannel = NotificationChannel(
            DEFAULT_CHANNEL, "Default", NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(defaultChannel)
    }

    companion object {

        private const val DEFAULT_CHANNEL = "default"

        const val TAG = "DeviceChargingWorker"
    }
}