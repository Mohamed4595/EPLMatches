package com.eplmatches.app.network

import com.google.common.truth.Truth.assertThat
import com.matches.domain.MatchModel
import com.matches.interactors.GetRemoteMatches
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMatchesTest {
    // system in test
    private lateinit var getMatches: GetRemoteMatches

    @Test
    fun `Get Matches From Network with Good Data`() = runBlocking {
        // setup
        val remoteMatchesService = FakeMatchesService.build(
            type = MatchesServiceResponseType.GoodData // good data
        )

        getMatches = GetRemoteMatches(
            service = remoteMatchesService
        )

        // Execute the use-case
        val emissions = getMatches.execute().toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<MatchModel>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<MatchModel>>(ProgressBarState.Idle))

        // Confirm second emission is data
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.isNotEmpty()).isTrue()

    }

    @Test
    fun `Get Matches From Network with Empty Data`() = runBlocking {
        // setup
        val remoteMatchesService = FakeMatchesService.build(
            type = MatchesServiceResponseType.EmptyList // good data
        )

        getMatches = GetRemoteMatches(
            service = remoteMatchesService
        )

        // Execute the use-case
        val emissions = getMatches.execute().toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<MatchModel>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<MatchModel>>(ProgressBarState.Idle))

        // Confirm second emission is data
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.isEmpty()).isTrue()

    }

    @Test
    fun `Get Matches From Network with Malformed Data`() = runBlocking {
        // setup
        val remoteMatchesService = FakeMatchesService.build(
            type = MatchesServiceResponseType.MalformedData // good data
        )

        getMatches = GetRemoteMatches(
            service = remoteMatchesService
        )

        // Execute the use-case
        val emissions = getMatches.execute().toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<MatchModel>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<MatchModel>>(ProgressBarState.Idle))

        // Confirm second emission is error response
        assertThat(emissions[2] is DataState.Error).isTrue()
        assertThat(((emissions[2] as DataState.Error).uiComponent as UIComponent.Dialog).title == "Network Data Error").isTrue()
    }

    @Test
    fun `Get Matches From Network with Error Http`() = runBlocking {
        // setup
        val remoteMatchesService = FakeMatchesService.build(
            type = MatchesServiceResponseType.MalformedData // good data
        )

        getMatches = GetRemoteMatches(
            service = remoteMatchesService
        )

        // Execute the use-case
        val emissions = getMatches.execute().toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<MatchModel>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<MatchModel>>(ProgressBarState.Idle))

        // Confirm second emission is error response
        assertThat(emissions[2] is DataState.Error).isTrue()
        assertThat(((emissions[2] as DataState.Error).uiComponent as UIComponent.Dialog).title == "Network Data Error").isTrue()
    }

}