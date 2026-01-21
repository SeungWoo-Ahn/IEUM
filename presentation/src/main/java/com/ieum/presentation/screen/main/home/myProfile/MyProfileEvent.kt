package com.ieum.presentation.screen.main.home.myProfile

import com.ieum.domain.model.post.PostType

sealed class MyProfileEvent {
    data class MoveEditPost(val postId: Int, val type: PostType) : MyProfileEvent()
}