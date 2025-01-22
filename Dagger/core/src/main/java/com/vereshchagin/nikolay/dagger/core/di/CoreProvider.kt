package com.vereshchagin.nikolay.dagger.core.di

import com.vereshchagin.nikolay.dagger.core.domain.repository.AnalyticsTracker
import com.vereshchagin.nikolay.dagger.core.domain.repository.FirstRepository
import com.vereshchagin.nikolay.dagger.core.domain.repository.SecondRepository

interface CoreProvider {

    @ConsoleAnalytics
    fun provideConsoleAnalyticsTracker(): AnalyticsTracker

    @FirebaseAnalytics
    fun provideFirebaseAnalyticsTracker(): AnalyticsTracker

    fun provideFirstRepository(): FirstRepository

    fun provideSecondRepository(): SecondRepository
}