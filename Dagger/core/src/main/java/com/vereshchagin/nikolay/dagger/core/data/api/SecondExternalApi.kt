package com.vereshchagin.nikolay.dagger.core.data.api

import com.vereshchagin.nikolay.dagger.core.data.api.response.FirstResponse
import com.vereshchagin.nikolay.dagger.core.data.api.response.SecondResponse

interface SecondExternalApi {

    suspend fun getSecondResponse(): SecondResponse
}