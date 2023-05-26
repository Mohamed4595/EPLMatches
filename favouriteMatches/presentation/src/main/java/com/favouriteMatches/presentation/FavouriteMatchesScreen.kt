package com.favouriteMatches.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.favouriteMatches.presentation.model.toFavouriteMatchesEvent
import com.matches.presentation.ui.component.MatchesListScreenContent
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.EPLTopAppBar
import com.mhmd.components.EmptyView
import com.mhmd.components.R
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UiState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun FavouriteMatchesScreen(
    uiState: UiState<FavouriteMatchesState>,
    events: (FavouriteMatchesEvents) -> Unit,
    navigateToBackScreen: () -> Unit,
    imageLoader: ImageLoader
) {
    DefaultScreenUI(
        queue = when (uiState) {
            is UiState.Error -> uiState.state.errorQueue
            is UiState.Loading -> uiState.state.errorQueue
            is UiState.Success -> uiState.state.errorQueue
        },
        onRemoveHeadFromQueue = {
            events(FavouriteMatchesEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = if (uiState is UiState.Loading) uiState.progressBarState else ProgressBarState.Idle,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                EPLTopAppBar(
                    prefixIconRes = R.drawable.ic_arrow_back,
                    onPrefixIconClick = {
                        navigateToBackScreen()
                    },
                    title = stringResource(id = R.string.favourites),
                )
                if (uiState is UiState.Error) {
                    EmptyView(modifier = Modifier.fillMaxHeight(), message = uiState.errorMessage)
                }
                if (uiState is UiState.Success) {
                    AnimatedVisibility(visible = true) {
                        MatchesListScreenContent(
                            true,
                            uiState.state.scrollToSelectedDay,
                            uiState.state.selectedDay,
                            uiState.state.daysList,
                            uiState.state.matches,
                            imageLoader
                        ) {
                            it.toFavouriteMatchesEvent()?.let { event ->
                                events(event)
                            }
                        }
                    }
                }
            }

        }
    }
}








