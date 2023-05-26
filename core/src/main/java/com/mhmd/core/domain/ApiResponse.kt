package com.mhmd.core.domain

import com.mhmd.constants.NetworkConstants
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.takeFrom

sealed class ApiResponse<T> {
    data class Fail<T>(
        val response: FailedResponse
    ) : ApiResponse<T>()

    data class Success<T>(
        val data: T? = null
    ) : ApiResponse<T>()
}

fun HttpRequestBuilder.pathUrl(path: String) {
    url {
        headers {
            append("X-Auth-Token", NetworkConstants.API_KEY)
        }
        takeFrom(NetworkConstants.BASE_URL)
        path("v4",path)
    }
}