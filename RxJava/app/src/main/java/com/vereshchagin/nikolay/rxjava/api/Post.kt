package com.vereshchagin.nikolay.rxjava.api

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
) {

    override fun toString(): String {
        return "Post(id=$id, title='$title', body='${body.substring(0..10)}...', userId=$userId)"
    }
}