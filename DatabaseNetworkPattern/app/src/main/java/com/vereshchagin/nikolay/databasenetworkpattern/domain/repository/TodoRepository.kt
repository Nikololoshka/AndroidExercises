package com.vereshchagin.nikolay.databasenetworkpattern.domain.repository

import com.vereshchagin.nikolay.databasenetworkpattern.data.api.response.TodoResponse

interface TodoRepository {

    suspend fun getAllTodos(): List<TodoResponse>
}