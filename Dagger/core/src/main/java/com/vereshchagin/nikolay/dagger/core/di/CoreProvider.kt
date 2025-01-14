package com.vereshchagin.nikolay.dagger.core.di

import com.vereshchagin.nikolay.dagger.core.domain.repository.FirstRepository
import com.vereshchagin.nikolay.dagger.core.domain.repository.SecondRepository

interface CoreProvider {

    fun provideFirstRepository(): FirstRepository

    fun provideSecondRepository(): SecondRepository
}