package com.example.vahan_app

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UniversityRefreshService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "UniversityRefreshServiceChannel"
    }

    private val refreshIntervalMs = 10 * 1000L // 10 seconds

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    private fun startForegroundService() {
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "University Refresh Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification to keep the service in the foreground
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("University Refresh Service")
            .setContentText("Refreshing university data...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification)

        // Start the data refresh loop
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                refreshUniversityData()
                delay(refreshIntervalMs)
            }
        }
    }

    private fun refreshUniversityData() {
        // Initialize Retrofit and the API service
        val retrofit = Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UniversityService::class.java)

        // Fetch university data
        val response = service.getUniversities().execute()

        if (response.isSuccessful) {
            val universities = response.body()
            if (universities != null) {
                // Update the data in your app or do any necessary processing here
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}
