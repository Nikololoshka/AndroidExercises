package com.vereshchagin.nikolay.dagger

import android.app.Application
import com.vereshchagin.nikolay.dagger.core.di.injector.App
import com.vereshchagin.nikolay.dagger.core.di.injector.AppDependenciesProvider
import com.vereshchagin.nikolay.dagger.di.AppComponent

class MainApplication : Application(), App {

    private val appComponent: AppComponent by lazy { AppComponent.create(this) }

    override fun onCreate() {
        super.onCreate()
        Instance = this
    }

    override fun appDependenciesProvider(): AppDependenciesProvider {
        return appComponent
    }

    companion object {
        lateinit var Instance: MainApplication
    }
}