package org.example

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import io.reactivex.rxjava3.subjects.UnicastSubject
import java.util.concurrent.TimeUnit

fun first() {
    Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
        .subscribeOn (Schedulers.io())
        .map {
            println("HAHAHA - mapThread = ${Thread.currentThread().name}")
        }
        .doOnSubscribe {
            println("HAHAHA - onSubscribeThread = ${Thread.currentThread().name}")
        }
        .subscribeOn(Schedulers.computation())
        .observeOn (Schedulers.single())
        .flatMap {
            println("HAHAHA - flatMapThread = ${Thread.currentThread().name}")
            Observable.just(it)
                .subscribeOn (Schedulers.io())
        }
        .subscribe {
            println("HAHAHA - subscribeThread = ${Thread.currentThread().name}")
        }

    /*
    Стадия подписки:
        1) subscribeOn(Schedulers.computation()) для всего выше
        2) doOnSubscribe: HAHAHA - onSubscribeThread = RxComputationThreadPool-1 - что логично
        3) subscribeOn (Schedulers.io()) для всего выше
        4) у Observable.timer через Schedulers.newThread() меняется scheduler
            проще говоря это как будто бы:
                 .subscribeOn(Schedulers.newThread()) <-- будет использоваться он
                 .subscribeOn(Schedulers.io())

    Стадия эмисси:
        1) Начинается все на Schedulers.newThread()
        2) map: HAHAHA - mapThread = RxNewThreadScheduler-1 - что логично
        3) observeOn(Schedulers.single()) для всего ниже
        4) flatMap: HAHAHA - flatMapThread = RxSingleScheduler-1 - что логично
        5) subscribeOn(Schedulers.io()), Observable.just на io теперь и эммисия тоже
        6) subscribe: HAHAHA - subscribeThread = RxCachedThreadScheduler-1
            observeOn работает только внутри flatMap, далее на основе его результата определяется scheduler
            там есть только Schedulers.io() для подписки и дальнейшей эмисии
            поэтому используется он затем в subscribe
     */

    Thread.sleep(10000)
}

fun second() {
    val subject = PublishSubject.create<String>()
    subject.onNext("1")
    subject.onNext("2")
    subject.onNext("3")
    subject.subscribe { println(it) }

    /*
    Будет ничего, PublishSubject получит только новые значения
     */

    val replaySubject = ReplaySubject.create<String>()
    replaySubject.onNext("1")
    replaySubject.onNext("2")
    replaySubject.onNext("3")
    replaySubject.subscribe { println(it) }

    val unicastSubject = UnicastSubject.create<String>()
    unicastSubject.onNext("1")
    unicastSubject.onNext("2")
    unicastSubject.onNext("3")
    unicastSubject.subscribe { println(it) }

    Thread.sleep(100000)
}

fun main() {
    // first()
    second()
}