package com.matches.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ScoreDto(
    val fullTime: ScoreTimeDto?=null,
    val halfTime: ScoreTimeDto?=null
)
