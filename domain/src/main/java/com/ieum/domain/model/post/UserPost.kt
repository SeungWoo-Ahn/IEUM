package com.ieum.domain.model.post

sealed class UserPost {
    abstract val shared: Boolean

    data class Wellness(
        val wellness: Post.Wellness,
        override val shared: Boolean,
    ) : UserPost()

    data class Daily(
        val daily: Post.Daily,
        override val shared: Boolean,
    ) : UserPost()
}