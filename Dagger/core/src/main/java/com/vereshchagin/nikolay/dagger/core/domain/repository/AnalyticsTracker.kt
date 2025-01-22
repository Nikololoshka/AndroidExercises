package com.vereshchagin.nikolay.dagger.core.domain.repository

interface AnalyticsTracker {
    fun track(info: String)
}