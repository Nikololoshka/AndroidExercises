package com.vereshchagin.nikolay.dagger.core.data.api

import com.vereshchagin.nikolay.dagger.core.data.api.response.FirstResponse

interface FirstExternalApi {

    suspend fun getFirstResponse(): FirstResponse
}