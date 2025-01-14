package com.vereshchagin.nikolay.dagger.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vereshchagin.nikolay.dagger.feature.home.domain.interceptor.HomeInterceptor
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Factory на самом деле не нужна, но пусть будет
 */
class HomeViewModel @AssistedInject constructor(
    private val interceptor: HomeInterceptor
) : ViewModel() {

    private val _state: MutableStateFlow<String> = MutableStateFlow("Nothing")
    val state: StateFlow<String> = _state.asStateFlow()

    fun fetchData() {
        _state.value = "Loading"

        viewModelScope.launch {
            val firstRequest = async { interceptor.getFirstData() }
            val secondRequest = async { interceptor.getSecondData() }

            try {
                val firstData = firstRequest.await()
                val secondData = secondRequest.await()
                _state.value = "${firstData.value} and ${secondData.value}"

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = "Error: $e"
            }
        }
    }

    @AssistedFactory
    interface HomeViewModelFactory {
        fun create(): HomeViewModel
    }

    companion object {
        fun provideFactory(factory: HomeViewModelFactory) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create() as T
            }
        }
    }
}