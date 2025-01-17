package com.vereshchagin.nikolay.databasenetworkpattern.data.repository

import android.content.Context
import android.util.Log
import com.vereshchagin.nikolay.databasenetworkpattern.data.api.TodoApi
import com.vereshchagin.nikolay.databasenetworkpattern.data.api.response.TodoResponse
import com.vereshchagin.nikolay.databasenetworkpattern.domain.repository.TodoRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Реализовать Interceptor, который будет логировать код ответа сервера.
 */
class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        Log.d("LogInterceptor", "Code: ${response.code}")
        return response
    }
}

class TodoRepositoryImpl : TodoRepository {

    private val client = OkHttpClient.Builder()
        .addInterceptor(LogInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(TodoApi::class.java)

    override suspend fun getAllTodos(): List<TodoResponse> {
        return api.todos().await()
    }
}