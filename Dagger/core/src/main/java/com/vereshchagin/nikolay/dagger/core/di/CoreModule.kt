package com.vereshchagin.nikolay.dagger.core.di

import com.vereshchagin.nikolay.dagger.core.data.repository.FirstRepositoryImpl
import com.vereshchagin.nikolay.dagger.core.data.repository.SecondRepositoryImpl
import com.vereshchagin.nikolay.dagger.core.domain.repository.FirstRepository
import com.vereshchagin.nikolay.dagger.core.domain.repository.SecondRepository
import dagger.Binds
import dagger.Module

@Module
interface CoreModule {

    @Binds
    fun provideFirstRepository(repository: FirstRepositoryImpl): FirstRepository

    @Binds
    fun provideSecondRepository(repository: SecondRepositoryImpl): SecondRepository
}