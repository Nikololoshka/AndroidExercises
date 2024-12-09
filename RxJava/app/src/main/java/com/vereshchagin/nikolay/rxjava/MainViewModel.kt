package com.vereshchagin.nikolay.rxjava

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vereshchagin.nikolay.rxjava.api.Post
import com.vereshchagin.nikolay.rxjava.api.PostsRemoteRepository
import com.vereshchagin.nikolay.rxjava.extentions.plusAssign
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val repository = PostsRemoteRepository()

    private val responseSubject = BehaviorSubject.createDefault("No data")
    val response: Observable<String> get() = responseSubject

    init {
        // requestDiscountCards()
    }

    fun makeRequest() {
        compositeDisposable += repository.requestPost()
            .doOnSubscribe { responseSubject.onNext("Loading") }
            .map { post -> post.toString() }
            .subscribe(
                { data -> responseSubject.onNext(data) },
                { error -> responseSubject.onNext("Error: $error") }
            )
    }

    /*
    Есть 2 сервера на которых лежат скидочные карты.
    Нужно получить эти данные и вывести в единый список. (zip и тд)

        а) Если 1 из запросов падает, то все равно выводить
            (найти метод RX для такого, чтоб самому не прописывать логику)

        б) Если 1 из запросов падает, то не выводить ничего
            (найти метод RX)

     */
    fun requestDiscountCards() {
        val serverFirst: Observable<List<Post>> = repository
            .requestPost(id = 1)
            .repeat(10)
            .toList()
            .toObservable()

        val serverSecond: Observable<List<Post>> = repository
            .requestPost(id = 2)
            .repeat(10)
            .toList()
            .toObservable()

        val serverSecondError: Observable<List<Post>> = Observable
            .error(Exception("Test exception"))


        compositeDisposable += Observable
            .zip(
                serverFirst.onErrorReturnItem(emptyList()),
                serverSecond.onErrorReturnItem(emptyList())
            ) { first, second -> first + second }
            .subscribe { items ->
                Log.d("MainViewModel", "Correct: ${items.size}")
            }

        // a)
        compositeDisposable += Observable
            .zip(
                serverFirst.onErrorReturnItem(emptyList()),
                serverSecondError.onErrorReturnItem(emptyList())
            ) { first, second -> first + second }
            .subscribe { items ->
                Log.d("MainViewModel", "With error continue: ${items.size}")
            }

        // б)
        compositeDisposable += Observable
            .zip(
                serverFirst,
                serverSecondError
            ) { first, second -> first + second }
            .subscribe(
                { items ->
                    Log.d("MainViewModel", "With error: ${items.size}")
                },
                { error ->
                    Log.d("MainViewModel", "With error: nothing")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}