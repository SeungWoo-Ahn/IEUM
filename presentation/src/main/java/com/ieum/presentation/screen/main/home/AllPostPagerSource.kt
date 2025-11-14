package com.ieum.presentation.screen.main.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.usecase.post.GetAllPostListUseCase

class AllPostPagerSource(
    private val getAllPostListUseCase: GetAllPostListUseCase,
    private val diagnosis: Diagnosis?,
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1
        return try {
            val response = getAllPostListUseCase(
                page = page,
                size = 5,
                diagnosis = diagnosis,
            ).getOrThrow()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1
            LoadResult.Page(response, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}