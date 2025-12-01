package com.ieum.presentation.screen.main.othersProfile

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ieum.domain.model.post.Post
import com.ieum.domain.usecase.user.GetOthersPostListUseCase

class OthersPostPagerSource(
    private val getOthersPostListUseCase: GetOthersPostListUseCase,
    private val id: Int,
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
            val response = getOthersPostListUseCase(
                page = page,
                size = 5,
                id = id,
            ).getOrThrow()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1
            LoadResult.Page(response, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}