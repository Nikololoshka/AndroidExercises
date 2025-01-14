package com.vereshchagin.nikolay.dagger.feature.home.domain.usecase

import com.vereshchagin.nikolay.dagger.core.domain.model.SecondData
import com.vereshchagin.nikolay.dagger.core.domain.repository.SecondRepository
import javax.inject.Inject

class GetSecondDataUseCase@Inject constructor(
    private val repository: SecondRepository
) {
    suspend operator fun invoke(): SecondData = repository.fetch()
}