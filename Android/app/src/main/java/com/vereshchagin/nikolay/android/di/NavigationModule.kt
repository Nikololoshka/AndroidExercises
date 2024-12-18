package com.vereshchagin.nikolay.android.di

import com.vereshchagin.nikolay.android.navigation.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NavigationModule {

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return Router()
    }
}