package com.eplmatches.app

import com.matches.data.network.model.MatchesResponseDto
import com.matches.data.network.model.toMatches
import com.matches.domain.MatchModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
fun serializeMatchesData(jsonData: String): List<MatchModel>? {
    return json.decodeFromString<MatchesResponseDto>(jsonData).toMatches()
}