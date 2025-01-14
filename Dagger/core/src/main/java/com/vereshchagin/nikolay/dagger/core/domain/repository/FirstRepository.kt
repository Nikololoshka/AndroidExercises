package com.vereshchagin.nikolay.dagger.core.domain.repository

import com.vereshchagin.nikolay.dagger.core.domain.model.FirstData

interface FirstRepository {
    suspend fun fetch(): FirstData
}