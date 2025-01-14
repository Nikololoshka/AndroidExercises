package com.vereshchagin.nikolay.dagger.core.domain.repository

import com.vereshchagin.nikolay.dagger.core.domain.model.SecondData

interface SecondRepository {
    suspend fun fetch(): SecondData
}