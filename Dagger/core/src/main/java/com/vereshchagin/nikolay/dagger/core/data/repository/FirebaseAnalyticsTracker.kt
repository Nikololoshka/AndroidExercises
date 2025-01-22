package com.vereshchagin.nikolay.dagger.core.data.repository

import android.content.Context
import com.vereshchagin.nikolay.dagger.core.domain.repository.AnalyticsTracker
import java.io.File
import javax.inject.Inject

class FirebaseAnalyticsTracker @Inject constructor(
    context: Context
) : AnalyticsTracker {

    private val firebaseFile = File(context.filesDir, "firebase_analytics.txt")

    override fun track(info: String) {
        firebaseFile.appendText(info + '\n')
    }
}