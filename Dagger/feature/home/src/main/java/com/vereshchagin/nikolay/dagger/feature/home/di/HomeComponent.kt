package com.vereshchagin.nikolay.dagger.feature.home.di

import com.vereshchagin.nikolay.dagger.core.di.CoreProvider
import com.vereshchagin.nikolay.dagger.feature.home.presentation.HomeFragment
import dagger.Component

@Component(
    dependencies = [CoreProvider::class]
)
interface HomeComponent {

    fun inject(fragment: HomeFragment)

    companion object {
        fun create(
            coreProvider: CoreProvider
        ): HomeComponent {
            return DaggerHomeComponent
                .factory()
                .create(coreProvider)

        }
    }

    @Component.Factory
    interface Factory {
        fun create(coreProvider: CoreProvider): HomeComponent
    }
}