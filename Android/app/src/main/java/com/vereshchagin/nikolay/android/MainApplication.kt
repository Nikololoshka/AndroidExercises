package com.vereshchagin.nikolay.android

import android.app.Application
import com.vereshchagin.nikolay.android.di.AppComponent

class MainApplication : Application() {

    val appComponent: AppComponent by lazy { AppComponent.create() }

    override fun onCreate() {
        super.onCreate()
        Instance = this
    }

    companion object {
        lateinit var Instance: MainApplication
    }
}