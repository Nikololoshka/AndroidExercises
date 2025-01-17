package com.vereshchagin.nikolay.databasenetworkpattern.data.api

import com.vereshchagin.nikolay.databasenetworkpattern.data.api.response.TodoResponse
import retrofit2.Call
import retrofit2.http.GET

interface TodoApi {

    @GET("/todos")
    fun todos(): Call<List<TodoResponse>>
}