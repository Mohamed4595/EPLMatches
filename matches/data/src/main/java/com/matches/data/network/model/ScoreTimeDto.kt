package com.matches.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ScoreTimeDto(val home: Int? = null, val away: Int? = null)
