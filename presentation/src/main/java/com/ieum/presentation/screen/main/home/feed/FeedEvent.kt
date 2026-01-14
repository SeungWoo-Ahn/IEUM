package com.ieum.presentation.screen.main.home.feed

import com.ieum.domain.model.post.PostType

sealed class FeedEvent {
    data object DeletePost : FeedEvent()

    data object MoveMyProfile : FeedEvent()

    data class MoveOthersProfile(val userId: Int) : FeedEvent()

    data class MoveEditPost(val postId: Int, val type: PostType) : FeedEvent()
}