package com.vereshchagin.nikolay.android.di

import com.vereshchagin.nikolay.android.MainActivity
import com.vereshchagin.nikolay.android.presentation.CommonFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: CommonFragment)

    companion object {
        fun create(): AppComponent {
            return DaggerAppComponent.create()
        }
    }
}