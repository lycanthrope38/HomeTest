package com.hometest.data.repository

import com.hometest.data.database.UserEntity
import com.hometest.data.mapper.toUserDetail
import com.hometest.data.mapper.toUserEntity
import com.hometest.data.mapper.toUserInfo
import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse
import org.junit.Assert.*
import org.junit.Test

class UserMapperTest {

    @Test
    fun userEntity_toUserInfo_mapsCorrectly() {
        val userEntity = UserEntity(1, "login", "avatarUrl", "htmlUrl")
        val userInfo = userEntity.toUserInfo()

        assertEquals(1, userInfo.id)
        assertEquals("login", userInfo.login)
        assertEquals("avatarUrl", userInfo.avatarUrl)
        assertEquals("htmlUrl", userInfo.htmlUrl)
    }

    @Test
    fun userDetailResponse_toUserDetail_mapsCorrectly() {
        val userDetailResponse = UserDetailResponse(1, "login", "avatarUrl", "htmlUrl", "location", 10, 20)
        val userDetail = userDetailResponse.toUserDetail()

        assertEquals(1, userDetail.id)
        assertEquals("login", userDetail.login)
        assertEquals("avatarUrl", userDetail.avatarUrl)
        assertEquals("htmlUrl", userDetail.htmlUrl)
        assertEquals("location", userDetail.location)
        assertEquals(10, userDetail.followers)
        assertEquals(20, userDetail.following)
    }

    @Test
    fun userDetailResponse_toUserDetail_handlesNullValues() {
        val userDetailResponse = UserDetailResponse(null, null, null, null, null, null, null)
        val userDetail = userDetailResponse.toUserDetail()

        assertEquals(0, userDetail.id)
        assertEquals("", userDetail.login)
        assertEquals("", userDetail.avatarUrl)
        assertEquals("", userDetail.htmlUrl)
        assertEquals("", userDetail.location)
        assertEquals(0, userDetail.followers)
        assertEquals(0, userDetail.following)
    }

    @Test
    fun userResponse_toUserEntity_mapsCorrectly() {
        val userResponse = UserResponse(1, "login", "avatarUrl", "htmlUrl")
        val userEntity = userResponse.toUserEntity()

        assertEquals(1, userEntity.id)
        assertEquals("login", userEntity.login)
        assertEquals("avatarUrl", userEntity.avatarUrl)
        assertEquals("htmlUrl", userEntity.htmlUrl)
    }

    @Test
    fun userResponse_toUserEntity_handlesNullValues() {
        val userResponse = UserResponse(null, null, null, null)
        val userEntity = userResponse.toUserEntity()

        assertEquals(0, userEntity.id)
        assertEquals("", userEntity.login)
        assertEquals("", userEntity.avatarUrl)
        assertEquals("", userEntity.htmlUrl)
    }
}