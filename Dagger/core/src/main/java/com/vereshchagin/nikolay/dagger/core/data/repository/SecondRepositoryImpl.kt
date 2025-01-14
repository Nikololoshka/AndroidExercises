package com.vereshchagin.nikolay.dagger.core.data.repository

import com.vereshchagin.nikolay.dagger.core.data.api.SecondExternalApi
import com.vereshchagin.nikolay.dagger.core.data.mapper.toData
import com.vereshchagin.nikolay.dagger.core.domain.model.SecondData
import com.vereshchagin.nikolay.dagger.core.domain.repository.SecondRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class SecondRepositoryImpl @Inject constructor(
    private val api: SecondExternalApi
): SecondRepository {
    override suspend fun fetch(): SecondData {
        delay(1000)
        return api.getSecondResponse().toData()
    }
}