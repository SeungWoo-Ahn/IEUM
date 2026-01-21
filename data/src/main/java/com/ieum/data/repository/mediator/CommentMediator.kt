package com.ieum.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.model.CommentEntity
import com.ieum.data.mapper.toEntity
import com.ieum.data.network.model.post.CommentDto

@OptIn(ExperimentalPagingApi::class)
class CommentMediator(
    private val db: IeumDatabase,
    private val getMyId: suspend  () -> Result<Int>,
    private val getCommentList: suspend (page: Int, size: Int) -> List<CommentDto>,
    private val deleteAll: suspend () -> Unit,
    private val insertAll: suspend (comments: List<CommentEntity>) -> Unit,
): RemoteMediator<Int, CommentEntity>() {
    private var lastRequestedPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CommentEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1.also { lastRequestedPage = it }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastRequestedPage + 1
            }
            val myId = getMyId().getOrThrow()
            val response = getCommentList(page, state.config.pageSize)
            if (response.isNotEmpty()) {
                lastRequestedPage = page
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deleteAll()
                }
                insertAll(response.map { it.toEntity(myId) })
            }

            MediatorResult.Success(endOfPaginationReached = response.size < state.config.pageSize)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}