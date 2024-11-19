package com.hometest.data.api

internal object ApiConstant {
    internal const val BASE_URL = "https://api.github.com/"

    internal const val HTTP_CONNECT_TIMEOUT = 60L
    internal const val HTTP_READ_TIMEOUT = 60L
    internal const val HTTP_WRITE_TIMEOUT = 60L

    internal const val HEADER_CONTENT_TYPE = "Content-Type"
    internal const val HEADER_ACCEPT = "accept"

    internal const val HEADER_CONTENT_TYPE_VALUE = "application/json"
}