package com.ieum.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ieum.data.database.IeumDatabase
import com.ieum.data.database.dao.PostDao
import com.ieum.data.database.model.PostEntity
import com.ieum.data.datasource.user.UserDataSource
import com.ieum.data.mapper.asBody
import com.ieum.data.mapper.toDomain
import com.ieum.data.mapper.toEntity
import com.ieum.data.repository.mediator.MyPostMediator
import com.ieum.data.repository.mediator.OthersPostMediator
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostType
import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.model.user.OthersProfile
import com.ieum.domain.model.user.PatchProfileRequest
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val db: IeumDatabase,
    private val userDataSource: UserDataSource,
    private val postDao: PostDao,
) : UserRepository {
    override suspend fun register(request: RegisterRequest) =
        userDataSource
            .register(request.asBody())

    override suspend fun getMyProfile(): MyProfile =
        userDataSource
            .getMyProfile()
            .toDomain()

    override suspend fun patchMyProfile(request: PatchProfileRequest): MyProfile =
        userDataSource
            .patchMyProfile(request.asBody())
            .toDomain()

    override suspend fun getOthersProfile(id: Int): OthersProfile =
        userDataSource
            .getOthersProfile(id)
            .toDomain()

    override suspend fun getMyPost(id: Int, type: PostType): Post =
        postDao.getMyPost(id, type.key)?.toDomain() ?: run {
            userDataSource.getMyPost(id, type.key)
                .toEntity()
                .also { postDao.insert(it) }
                .toDomain()
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMyPostListFlow(type: PostType): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { postDao.getMyPostPagingSource(type.key) },
            remoteMediator = MyPostMediator(
                db = db,
                getMyPostList = { page, size ->
                    userDataSource.getMyPostList(
                        page = page,
                        size = size,
                        type = type.key,
                        fromDate = null,
                        toDate = null,
                    ).posts
                },
                deleteMyPostList = { postDao.deleteMyPostList(type.key) },
                insertAll = postDao::insertAll
            )
        )
            .flow
            .map { pagingData ->
                pagingData.map(PostEntity::toDomain)
            }

    @OptIn(ExperimentalPagingApi::class)
    override fun getOthersPostListFlow(id: Int): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { postDao.getOthersPostPagingSource(id) },
            remoteMediator = OthersPostMediator(
                db = db,
                getOthersPostList = { page, size -> userDataSource.getOtherPostList(
                    page = page,
                    size = size,
                    id = id
                ) },
                deleteOthersPostList = { postDao.deleteOthersPostList(id) },
                insertAll = postDao::insertAll
            )
        )
            .flow
            .map { pagingData ->
                pagingData.map(PostEntity::toDomain)
            }

    override suspend fun withdraw() {
        userDataSource.withdraw()
    }
}
