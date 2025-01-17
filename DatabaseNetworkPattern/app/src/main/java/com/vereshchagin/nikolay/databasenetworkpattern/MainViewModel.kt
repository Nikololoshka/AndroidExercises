package com.vereshchagin.nikolay.databasenetworkpattern

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vereshchagin.nikolay.databasenetworkpattern.data.db.FlowersDatabase
import com.vereshchagin.nikolay.databasenetworkpattern.data.repository.BouquetsRepositoryImpl
import com.vereshchagin.nikolay.databasenetworkpattern.data.repository.TodoRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(
    context: Context
) : ViewModel() {

    private val db = FlowersDatabase.getDatabase(context)
    private val repository = BouquetsRepositoryImpl(db.bouquetDao(), db.flowersDao())
    private val todoRepository = TodoRepositoryImpl()

    init {
        viewModelScope.launch {
            repository
                .getAvailableBouquets()
                .collect { bouquets ->
                    bouquets.forEach {
                        Log.d("MainViewModel", it.toString())
                    }
            }
        }
    }

    fun buySomething() {
        viewModelScope.launch {
            val bouquet =repository.getAvailableBouquets().first().maxBy { it.stock }
            repository.buyBouquet(bouquet.id)
        }
    }

    fun getAllTodos() {
        viewModelScope.launch {
            val todos = todoRepository.getAllTodos()
            Log.d("MainViewModel", todos.toString())
        }
    }

    companion object {
        fun provideFactory(
            context: Context
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(context) as T
            }
        }
    }
}