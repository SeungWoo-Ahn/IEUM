package com.ieum.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.dao.CommentDao
import com.ieum.data.database.dao.PostDao
import com.ieum.data.database.model.CommentEntity
import com.ieum.data.database.model.PostEntity
import com.ieum.data.datasource.post.PostDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.mapper.toEntity
import com.ieum.data.repository.mediator.AllPostMediator
import com.ieum.data.repository.mediator.CommentMediator
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.model.user.Diagnosis
import com.ieum.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val db: IeumDatabase,
    private val postDataSource: PostDataSource,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
) : PostRepository {
    override suspend fun postWellness(request: PostWellnessRequest) {
        postDataSource
            .postWellness(
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .also {
                postDao.insert(it.toEntity())
            }
    }

    override suspend fun patchWellness(id: Int, request: PostWellnessRequest) {
        postDataSource
            .patchWellness(
                id = id,
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .also {
                postDao.update(it.toEntity())
            }
    }

    override suspend fun deleteWellness(id: Int) {
        postDataSource.deleteWellness(id)
            .also {
                postDao.deleteById(id, PostType.WELLNESS.key)
            }
    }

    override suspend fun postDaily(request: PostDailyRequest) {
        postDataSource
            .postDaily(
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .also {
                postDao.insert(it.toEntity())
            }
    }

    override suspend fun patchDaily(id: Int, request: PostDailyRequest) {
        postDataSource
            .patchDaily(
                id = id,
                body = request.asBody(),
                fileList = request.imageList.map(ImageSource.Local::file)
            )
            .also {
                postDao.update(it.toEntity())
            }
    }

    override suspend fun deleteDaily(id: Int) {
        postDataSource.deleteDaily(id)
            .also {
                postDao.deleteById(id, PostType.DAILY.key)
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPostListFlow(
        diagnosis: Diagnosis?,
        getMyId: suspend () -> Result<Int>
    ): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { postDao.getAllPostPagingSource(diagnosis?.key) },
            remoteMediator = AllPostMediator(
                db = db,
                getMyId = getMyId,
                getAllPostList = { page, size ->
                    postDataSource.getAllPostList(
                        page = page,
                        size = size,
                        diagnosis = diagnosis?.key
                    )
                },
                deleteAllPostList = postDao::deleteAllPostList,
                insertAll = postDao::insertAll,
            )
        )
            .flow
            .map { pagingData ->
                pagingData.map(PostEntity::toDomain)
            }

    override suspend fun likePost(id: Int, type: PostType) {
        postDao.likePost(id, type.key)
        try {
            postDataSource.likePost(id, type.key)
        } catch (e: Exception) {
            postDao.unLikePost(id, type.key)
            throw e
        }
    }

    override suspend fun unlikePost(id: Int, type: PostType) {
        postDao.unLikePost(id, type.key)
        try {
            postDataSource.unlikePost(id, type.key)
        } catch (e: Exception) {
            postDao.likePost(id, type.key)
            throw e
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCommentListFlow(
        postId: Int,
        type: PostType,
        getMyId: suspend () -> Result<Int>,
    ): Flow<PagingData<Comment>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { commentDao.getCommentPagingSource() },
            remoteMediator = CommentMediator(
                db = db,
                getMyId = getMyId,
                getCommentList = { page, size -> postDataSource.getCommentList(
                    page = page,
                    size = size,
                    postId = postId,
                    type = type.key
                ) },
                deleteAll = commentDao::deleteAll,
                insertAll = commentDao::insertAll,
            )
        )
            .flow
            .map { pagingData ->
                pagingData.map(CommentEntity::toDomain)
            }

    override suspend fun postComment(request: PostCommentRequest) {
        postDataSource
            .postComment(
                postId = request.postId,
                type = request.postType.key,
                body = request.asBody()
            )
            .also {
                commentDao.insert(it.toEntity())
            }
    }

    override suspend fun deleteComment(postId: Int, type: PostType, commentId: Int) {
        postDataSource
            .deleteComment(
                postId = postId,
                type = type.key,
                commentId = commentId,
            )
            .also {
                commentDao.deleteById(commentId)
            }
    }
}