package com.example.movieapp.interceptor

import com.example.movieapp.utils.Constants.API_KEY_PARAM
import com.example.movieapp.utils.Constants.KEY
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val httpUrl = chain.request().url.newBuilder().addQueryParameter(API_KEY_PARAM, KEY).build()
        val requestBuilder = chain.request().newBuilder().url(httpUrl).build()
        return chain.proceed(requestBuilder)
    }
}