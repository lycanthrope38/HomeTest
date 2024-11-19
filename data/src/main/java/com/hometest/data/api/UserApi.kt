package com.hometest.data.api

import com.hometest.data.model.UserDetailResponse
import com.hometest.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Long,
        @Query("per_page") perPage: Int,
    ): List<UserResponse>

    @GET("users/{login_username}")
    suspend fun getUserDetails(@Path("login_username") userName: String): UserDetailResponse
}