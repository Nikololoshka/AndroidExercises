package com.vereshchagin.nikolay.databasenetworkpattern.data.api.response

data class TodoResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)