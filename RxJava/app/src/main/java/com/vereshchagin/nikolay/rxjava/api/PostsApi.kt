package com.vereshchagin.nikolay.rxjava.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int): Single<Post>
}