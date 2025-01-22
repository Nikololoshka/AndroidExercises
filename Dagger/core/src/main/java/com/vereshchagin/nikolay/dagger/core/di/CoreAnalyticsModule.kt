package com.vereshchagin.nikolay.dagger.core.di

import com.vereshchagin.nikolay.dagger.core.data.repository.ConsoleAnalyticsTracker
import com.vereshchagin.nikolay.dagger.core.data.repository.FirebaseAnalyticsTracker
import com.vereshchagin.nikolay.dagger.core.domain.repository.AnalyticsTracker
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseAnalytics

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsoleAnalytics

@Module
interface CoreAnalyticsModule {

    @Binds
    @ConsoleAnalytics
    fun bindConsoleAnalyticsTracker(tracker: ConsoleAnalyticsTracker): AnalyticsTracker

    @Binds
    @FirebaseAnalytics
    fun bindFirebaseAnalyticsTracker(tracker: FirebaseAnalyticsTracker): AnalyticsTracker
}