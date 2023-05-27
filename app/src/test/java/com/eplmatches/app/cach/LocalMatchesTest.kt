package com.eplmatches.app.cach

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
import kotlinx.coroutines.flow.toList
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
        // Execute the use-case
        val emissions = insertMatch.execute(matchesToInsert).toList()

        // Confirm second emission is data
        assertThat(emissions[0] is DataState.Success).isTrue()
        assertThat((emissions[0] as DataState.Success).data ?: false).isTrue()

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
        // Execute the use-case
        val emissions = insertMatch.execute(matchesToInsert).toList()

        // Confirm second emission is data
        assertThat(emissions[0] is DataState.Success).isTrue()
        assertThat((emissions[0] as DataState.Success).data ?: false).isFalse()

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
        // Execute the use-case
        val emissions = deleteMatch.execute(2).toList()

        // Confirm second emission is data
        assertThat(emissions[0] is DataState.Success).isTrue()
        assertThat((emissions[0] as DataState.Success).data ?: false).isTrue()
    }

    @Test
    fun `Delete Not Existing Match`() = runBlocking {
        // Execute the use-case
        val emissions = deleteMatch.execute(3).toList()

        // Confirm second emission is data
        assertThat(emissions[0] is DataState.Success).isTrue()
        assertThat((emissions[0] as DataState.Success).data ?: false).isFalse()
    }
}