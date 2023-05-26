package com.matches.domain

import java.time.LocalDateTime

data class MatchModel(
    val id: Long,
    val status: String,
    val date: LocalDateTime? = null,
    val homeTeam: TeamModel,
    val awayTeam: TeamModel,
    val score: ScoreModel?
)













