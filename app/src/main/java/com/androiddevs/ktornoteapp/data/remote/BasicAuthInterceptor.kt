package com.androiddevs.ktornoteapp.data.remote

import com.androiddevs.ktornoteapp.other.Constants.IGNORE_AUTH_URLS
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor : Interceptor {

    var email: String? = null
    var password: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.encodedPath in IGNORE_AUTH_URLS) {
            return chain.proceed(request)
        }
        val authenticatedRequest = request.newBuilder()
            .header(
                name = "Authorization",
                Credentials.basic(
                    username = email ?: "",
                    password = password ?: ""
                )
            )
            .build()
        return chain.proceed(authenticatedRequest)
    }
}