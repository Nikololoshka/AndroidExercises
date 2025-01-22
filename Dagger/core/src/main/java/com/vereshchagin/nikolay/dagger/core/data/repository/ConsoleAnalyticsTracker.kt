package com.vereshchagin.nikolay.dagger.core.data.repository

import android.util.Log
import com.vereshchagin.nikolay.dagger.core.domain.repository.AnalyticsTracker
import javax.inject.Inject

class ConsoleAnalyticsTracker @Inject constructor() : AnalyticsTracker {
    override fun track(info: String) {
        Log.d("ConsoleAnalyticsTracker", "track: $info")
    }
}