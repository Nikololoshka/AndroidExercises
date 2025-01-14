package com.vereshchagin.nikolay.dagger.feature.home.domain.usecase

import com.vereshchagin.nikolay.dagger.core.domain.model.FirstData
import com.vereshchagin.nikolay.dagger.core.domain.repository.FirstRepository
import javax.inject.Inject

class GetFirstDataUseCase @Inject constructor(
    private val repository: FirstRepository
) {
    suspend operator fun invoke(): FirstData = repository.fetch()
}