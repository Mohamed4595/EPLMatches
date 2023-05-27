package com.eplmatches.app.cach

import com.matches.data.cach.MatchDao
import com.matches.data.cach.model.MatchEntity
import com.matches.data.cach.model.toMatchEntity
import com.matches.domain.MatchModel
import com.matches.domain.ScoreModel
import com.matches.domain.ScoreTimeModel
import com.matches.domain.TeamModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class FakeMatchesDao : MatchDao {

    private val matches = mutableListOf<MatchEntity>(
        MatchModel(
            id = 2,
            status = "Timed",
            date = LocalDateTime.now(),
            homeTeam = TeamModel(
                teamId = 1L,
                teamName = "Team Home",
                teamImage = ""
            ),
            awayTeam = TeamModel(
                teamId = 2L,
                teamName = "Away Home",
                teamImage = ""
            ),
            score = ScoreModel(
                fullTime = ScoreTimeModel(
                    home = 1,
                    away = 2
                ),
                halfTime = ScoreTimeModel(
                    home = 1,
                    away = 2
                )
            )
        ).toMatchEntity()
    )


    override suspend fun insertMatch(match: MatchEntity): Long {
        return if (matches.find { match.id == it.id } != null) 0L else {
            matches.add(match)
            1L
        }
    }

    override suspend fun deleteMatch(primaryKey: Int): Int {
        val item = matches.find { primaryKey == it.id }

        return if (matches.isEmpty()||matches.find { primaryKey == it.id } == null) 0 else {
            matches.remove(item)
            1
        }
    }

    override fun getMatches(): Flow<List<MatchEntity>> {
        return flow { emit(matches) }
    }
}