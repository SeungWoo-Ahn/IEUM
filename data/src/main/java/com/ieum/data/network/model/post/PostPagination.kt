package com.ieum.data.network.model.post

import kotlinx.serialization.Serializable

/**
 * @param currentPage 현재 페이지 번호
 * @param perPage 페이지 당 항목 수
 * @param totalItems 전체 포스트 수
 * @param totalPages 전체 페이지 수
 */
@Serializable
data class PostPagination(
    val currentPage: Int,
    val perPage: Int,
    val totalItems: Int,
    val totalPages: Int,
)
