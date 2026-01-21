@file:OptIn(ExperimentalPagingApi::class)

package com.ieum.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.model.PostEntity
import com.ieum.data.mapper.toEntity
import com.ieum.data.network.model.post.MyPostDto

class MyPostMediator(
    private val db: IeumDatabase,
    private val getMyPostList: suspend (page: Int, size: Int) -> List<MyPostDto>,
    private val deleteMyPostList: suspend () -> Unit,
    private val insertAll: suspend (posts: List<PostEntity>) -> Unit,
) : RemoteMediator<Int, PostEntity>() {
    private var lastRequestedPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1.also { lastRequestedPage = it }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastRequestedPage + 1
            }
            val response = getMyPostList(page, state.config.pageSize)
            if (response.isNotEmpty()) {
                lastRequestedPage = page
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deleteMyPostList()
                }
                insertAll(response.map(MyPostDto::toEntity))
            }

            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}