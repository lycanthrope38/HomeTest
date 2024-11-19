package com.hometest.data

import com.hometest.data.api.ApiConstant.HEADER_ACCEPT
import com.hometest.data.api.ApiConstant.HEADER_CONTENT_TYPE
import com.hometest.data.api.ApiConstant.HEADER_CONTENT_TYPE_VALUE
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
            .header(HEADER_ACCEPT, "application/json;charset=UTF-8")
            .build()
        return chain.proceed(request)
    }
}