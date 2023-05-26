package com.matches.data.network.model

import com.matches.domain.MatchModel
import com.matches.domain.ScoreModel
import com.matches.domain.ScoreTimeModel
import com.matches.domain.TeamModel
import com.mhmd.core.util.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    val id: Long? = null,
    val status: String? = null,
    val utcDate: String? = null,
    val homeTeam: TeamDto? = null,
    val awayTeam: TeamDto? = null,
    val score: ScoreDto? = null
)

fun MatchDto.toMatch(): MatchModel? {
    if (id == null || homeTeam == null || awayTeam == null || status == null)
        return null
    return MatchModel(
        id = id,
        status = status,
        date = utcDate?.toLocalDateTime(),
        score = score?.toScore(),
        awayTeam = awayTeam.toTeam(),
        homeTeam = homeTeam.toTeam()
    )
}


private fun ScoreDto.toScore(): ScoreModel? {
    if (halfTime == null)
        return null
    return ScoreModel(
        fullTime = fullTime?.toScoreTime(),
        halfTime = halfTime.toScoreTime()
    )
}

private fun ScoreTimeDto.toScoreTime(): ScoreTimeModel? {
    if (home == null || away == null)
        return null
    return ScoreTimeModel(
        home = home,
        away = away,
    )
}

private fun TeamDto.toTeam(): TeamModel {
    return TeamModel(
        teamId = id,
        teamName = shortName,
        teamImage = crest
    )

}











