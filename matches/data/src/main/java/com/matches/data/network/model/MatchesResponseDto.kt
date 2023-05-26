package com.matches.data.network.model

import com.matches.domain.MatchModel
import kotlinx.serialization.Serializable

@Serializable
data class MatchesResponseDto(
    val matches: List<MatchDto>? = null
)

fun MatchesResponseDto.toMatches(): List<MatchModel>? {
    if (matches == null)
        return null
    return matches.mapNotNull { it.toMatch() }
}