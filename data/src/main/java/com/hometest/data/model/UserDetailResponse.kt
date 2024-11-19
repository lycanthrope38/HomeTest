package com.hometest.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("login") val login: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("followers") val followers: Int? = null,
    @SerializedName("following") val following: Int? = null,
)