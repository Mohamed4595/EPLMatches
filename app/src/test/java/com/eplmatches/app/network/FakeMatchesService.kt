package com.eplmatches.app.network

import com.mhmd.core.data.MatchesDataEmpty
import com.mhmd.core.data.MatchesDataMalformed
import com.mhmd.core.data.MatchesDataValid
import com.matches.data.network.EndPoints
import com.matches.data.network.MatchesService
import com.matches.data.network.MatchesServiceImpl
import com.mhmd.constants.NetworkConstants
import com.mhmd.constants.NetworkConstants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.http.hostWithPort

class FakeMatchesService {

    companion object Factory {

        private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
        private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

        fun build(
            type: MatchesServiceResponseType
        ): MatchesService {
            val client = HttpClient(MockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys =
                                true // if the server sends extra fields, ignore them
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                engine {
                    addHandler { request ->
                        when (request.url.fullUrl) {
                            "${BASE_URL}v4/${EndPoints.COMPETITIONS}${EndPoints.PL}${EndPoints.MATCHES}?season=2022" -> {
                                val responseHeaders = headersOf(
                                    "Content-Type" to listOf("application/json", "charset=utf-8"),
                                    "X-Auth-Token" to listOf(NetworkConstants.API_KEY)
                                )
                                when (type) {
                                    is MatchesServiceResponseType.EmptyList -> {
                                        respond(
                                            MatchesDataEmpty.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MatchesServiceResponseType.MalformedData -> {
                                        respond(
                                            MatchesDataMalformed.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MatchesServiceResponseType.GoodData -> {
                                        respond(
                                            MatchesDataValid.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MatchesServiceResponseType.Http404 -> {
                                        respond(
                                            MatchesDataEmpty.data,
                                            status = HttpStatusCode.NotFound,
                                            headers = responseHeaders
                                        )
                                    }
                                }
                            }

                            else -> error("Unhandled ${request.url.fullUrl}")
                        }
                    }
                }
            }
            return MatchesServiceImpl(client)
        }
    }
}