package com.matches.data.network

import com.matches.data.network.model.MatchesResponseDto
import com.matches.data.network.model.toMatches
import com.matches.domain.MatchModel
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.pathUrl
import com.mhmd.core.domain.toFailedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MatchesServiceImpl(private val client: HttpClient) : MatchesService {
    override suspend fun getMatches(): ApiResponse<List<MatchModel>> {

        val httpResponse: HttpResponse = client.get {
            pathUrl(EndPoints.COMPETITIONS + EndPoints.PL + EndPoints.MATCHES)
            parameter("season", 2022)
        }
        return if (httpResponse.status.value in 200..299) {
            val response: MatchesResponseDto = httpResponse.receive()
            ApiResponse.Success(response.toMatches())
        } else {
            val response: FailedResponseDto = httpResponse.receive()
            ApiResponse.Fail(response.toFailedResponse())
        }

    }
}

