package com.vereshchagin.nikolay.dagger.core.data.mapper

import com.vereshchagin.nikolay.dagger.core.data.api.response.FirstResponse
import com.vereshchagin.nikolay.dagger.core.data.api.response.SecondResponse
import com.vereshchagin.nikolay.dagger.core.domain.model.FirstData
import com.vereshchagin.nikolay.dagger.core.domain.model.SecondData


fun FirstResponse.toData(): FirstData {
    return FirstData(value)
}

fun SecondResponse.toData(): SecondData {
    return SecondData(value)
}