package com.hometest.data.mapper

import com.hometest.data.database.UserEntity
import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse
import com.hometest.domain.model.UserDetail
import com.hometest.domain.model.UserInfo

fun UserEntity.toUserInfo() = UserInfo(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

fun UserDetailResponse.toUserDetail() = UserDetail(
    id = id ?: 0,
    login = login.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    htmlUrl = htmlUrl.orEmpty(),
    location = location.orEmpty(),
    followers = followers ?: 0,
    following = following ?: 0
)

fun UserResponse.toUserEntity() = UserEntity(
    id = id ?: 0,
    login = login.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    htmlUrl = htmlUrl.orEmpty()
)