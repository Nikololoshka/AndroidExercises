package com.vereshchagin.nikolay.dagger.feature.home.domain.interceptor

import com.vereshchagin.nikolay.dagger.feature.home.domain.usecase.GetFirstDataUseCase
import com.vereshchagin.nikolay.dagger.feature.home.domain.usecase.GetSecondDataUseCase
import javax.inject.Inject

class HomeInterceptor @Inject constructor(
    private val getFirstDataUseCase: GetFirstDataUseCase,
    private val getSecondDataUseCase: GetSecondDataUseCase
) {
    suspend fun getFirstData() = getFirstDataUseCase()
    suspend fun getSecondData() = getSecondDataUseCase()
}