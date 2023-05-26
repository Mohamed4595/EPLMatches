package com.eplmatches.app

import com.google.common.truth.Truth.assertThat
import com.matches.domain.MatchModel
import com.matches.domain.ScoreModel
import com.matches.domain.ScoreTimeModel
import com.matches.domain.TeamModel
import com.matches.interactors.DeleteMatch
import com.matches.interactors.GetLocalMatches
import com.matches.interactors.InsertMatch
import com.mhmd.core.domain.DataState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class LocalMatchesTest {
    private lateinit var getLocalMatches: GetLocalMatches
    private lateinit var insertMatch: InsertMatch
    private lateinit var deleteMatch: DeleteMatch

    private lateinit var fakeMatchesDao: FakeMatchesDao

    @Before
    fun setup() {
        fakeMatchesDao = FakeMatchesDao()
        getLocalMatches = GetLocalMatches(fakeMatchesDao)
        insertMatch = InsertMatch(fakeMatchesDao)
        deleteMatch = DeleteMatch(fakeMatchesDao)

    }

    @Test
    fun `Insert New Match`() = runBlocking {
        val matchesToInsert = MatchModel(
            id = 1,
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
        )
        val result = insertMatch.execute(matchesToInsert)

        result.collectLatest {
            when (it) {
                is DataState.Error -> assertThat(false).isTrue()
                is DataState.Loading -> assertThat(true).isTrue()
                is DataState.Success -> assertThat(it.data).isTrue()
            }
        }
    }

    @Test
    fun `Insert Existing Match`() = runBlocking {
        val matchesToInsert = MatchModel(
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
        )
        val result = insertMatch.execute(matchesToInsert)

        result.collectLatest {
            when (it) {
                is DataState.Error -> assertThat(false).isTrue()
                is DataState.Loading -> assertThat(true).isTrue()
                is DataState.Success -> assertThat(it.data).isFalse()
            }
        }
    }

    @Test
    fun `Get all Matches`() = runBlocking {
        val matches = getLocalMatches.execute()
        matches.collectLatest {
            when (it) {
                is DataState.Error -> assertThat(false).isTrue()
                is DataState.Loading -> assertThat(true).isTrue()
                is DataState.Success -> assertThat(it.data).isNotEmpty()
            }
        }
    }

    @Test
    fun `Delete Match`() = runBlocking {
        val result = deleteMatch.execute(2)
        result.collectLatest {
            when (it) {
                is DataState.Error -> assertThat(false).isTrue()
                is DataState.Loading -> assertThat(true).isTrue()
                is DataState.Success -> assertThat(it.data).isTrue()
            }
        }
    }

    @Test
    fun `Delete Not Existing Match`() = runBlocking {
        val result = deleteMatch.execute(3)
        result.collectLatest {
            when (it) {
                is DataState.Error -> assertThat(false).isTrue()
                is DataState.Loading -> assertThat(true).isTrue()
                is DataState.Success -> assertThat(it.data).isFalse()
            }
        }
    }
}