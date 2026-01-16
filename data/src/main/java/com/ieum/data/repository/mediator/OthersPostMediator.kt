package com.ieum.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.model.PostEntity
import com.ieum.data.mapper.toEntity
import com.ieum.data.network.model.post.OtherPostDto

@OptIn(ExperimentalPagingApi::class)
class OthersPostMediator(
    private val db: IeumDatabase,
    private val userId: Int,
    private val getOthersPostList: suspend  (page: Int, size: Int) -> List<OtherPostDto>,
    private val deleteOthersPostList: suspend () -> Unit,
    private val insertAll: suspend (posts: List<PostEntity>) -> Unit,
) : RemoteMediator<Int, PostEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (state.pages.size + 1)
                }
            }
            val response = getOthersPostList(page, state.config.pageSize)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deleteOthersPostList()
                }
                insertAll(response.map { it.toEntity(userId) })
            }

            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}