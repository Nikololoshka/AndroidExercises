package com.vereshchagin.nikolay.dagger.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        CoreNetworkModule::class,
        CoreModule::class
    ]
)
@Singleton
interface CoreComponent : CoreProvider {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    companion object {

        /**
         * Context тут не используется, но нужен будет в целом
         */
        fun create(context: Context): CoreComponent {
            return DaggerCoreComponent.factory()
                .create(context)
        }
    }
}