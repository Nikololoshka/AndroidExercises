package com.vereshchagin.nikolay.dagger.di

import android.content.Context
import com.vereshchagin.nikolay.dagger.core.di.CoreComponent
import com.vereshchagin.nikolay.dagger.core.di.CoreProvider
import com.vereshchagin.nikolay.dagger.core.di.injector.AppDependenciesProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CoreProvider::class
    ]
)
interface AppComponent: AppDependenciesProvider {

    @Component.Factory
    interface Factory {
        fun create(
            coreProvider: CoreProvider
        ): AppComponent
    }

    companion object {

        fun create(context: Context): AppComponent {
            val coreProvider = CoreComponent.create(context)

            return DaggerAppComponent
                .factory()
                .create(coreProvider)
        }
    }
}