package com.ieum.presentation.screen.main.home

import androidx.lifecycle.ViewModel
import com.ieum.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    postRepository: PostRepository,
) : ViewModel() {
    val postList = postRepository.getPostList()
}