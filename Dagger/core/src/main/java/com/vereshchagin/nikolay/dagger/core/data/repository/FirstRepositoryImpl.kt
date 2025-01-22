package com.vereshchagin.nikolay.dagger.core.data.repository

import com.vereshchagin.nikolay.dagger.core.data.api.FirstExternalApi
import com.vereshchagin.nikolay.dagger.core.data.mapper.toData
import com.vereshchagin.nikolay.dagger.core.di.FirebaseAnalytics
import com.vereshchagin.nikolay.dagger.core.domain.model.FirstData
import com.vereshchagin.nikolay.dagger.core.domain.repository.AnalyticsTracker
import com.vereshchagin.nikolay.dagger.core.domain.repository.FirstRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class FirstRepositoryImpl @Inject constructor(
    private val api: FirstExternalApi,
    @FirebaseAnalytics private val tracker: AnalyticsTracker
) : FirstRepository {
    override suspend fun fetch(): FirstData {
        tracker.track("FirstRepositoryImpl.fetch() called")

        delay(1000)
        return api.getFirstResponse().toData()
    }
}