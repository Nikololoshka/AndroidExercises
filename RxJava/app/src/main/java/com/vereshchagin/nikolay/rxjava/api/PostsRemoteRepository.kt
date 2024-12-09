package com.vereshchagin.nikolay.rxjava.api

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PostsRemoteRepository {

    private val api = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .client(
            OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BASIC)
                }
            )
            .build()
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PostsApi::class.java)

    fun requestPost(id: Int = 1): Single<Post> {
        return api.getPost(id)
            .subscribeOn(Schedulers.io())
    }
}