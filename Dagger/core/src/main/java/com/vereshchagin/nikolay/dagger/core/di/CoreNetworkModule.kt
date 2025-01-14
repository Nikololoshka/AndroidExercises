package com.vereshchagin.nikolay.dagger.core.di

import com.vereshchagin.nikolay.dagger.core.data.api.FirstExternalApi
import com.vereshchagin.nikolay.dagger.core.data.api.SecondExternalApi
import com.vereshchagin.nikolay.dagger.core.data.api.response.FirstResponse
import com.vereshchagin.nikolay.dagger.core.data.api.response.SecondResponse
import dagger.Module
import dagger.Provides
import kotlin.random.Random

@Module
class CoreNetworkModule {

    @Provides
    fun provideFirstExternalApi(): FirstExternalApi = object : FirstExternalApi {
        override suspend fun getFirstResponse(): FirstResponse {
            return FirstResponse("Some value ${Random.nextDouble()}")
        }
    }

    @Provides
    fun provideSecondExternalApi(): SecondExternalApi = object : SecondExternalApi {
        override suspend fun getSecondResponse(): SecondResponse {
            return SecondResponse(Random.nextInt())
        }
    }
}