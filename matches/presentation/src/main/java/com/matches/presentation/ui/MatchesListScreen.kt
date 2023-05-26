package com.matches.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import com.matches.presentation.ui.component.FavouriteSection
import com.matches.presentation.ui.component.MatchesListScreenContent
import com.mhmd.components.AppBarDivider
import com.mhmd.components.DefaultScreenUI
import com.mhmd.components.EPLTopAppBar
import com.mhmd.components.EmptyView
import com.mhmd.components.IconButton
import com.mhmd.components.R
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UiState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MatchesListScreen(
    uiState: UiState<MatchesListState>,
    events: (MatchesListEvents) -> Unit,
    navigateToFavouriteScreen: () -> Unit,
    imageLoader: ImageLoader
) {
    DefaultScreenUI(
        queue = when (uiState) {
            is UiState.Error -> uiState.state.errorQueue
            is UiState.Loading -> uiState.state.errorQueue
            is UiState.Success -> uiState.state.errorQueue
        },
        onRemoveHeadFromQueue = {
            events(MatchesListEvents.OnRemoveHeadFromQueue)
        },
        progressBarState = if (uiState is UiState.Loading) uiState.progressBarState else ProgressBarState.Idle,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                EPLTopAppBar(
                    title = stringResource(id = R.string.matches),
                    isDivider = uiState !is UiState.Success,
                    suffixContent = {
                        IconButton(
                            iconRes = R.drawable.ic_heart_outlined,
                            iconColor = MaterialTheme.colors.onBackground
                        ) {
                            navigateToFavouriteScreen()

                        }
                    }
                )
                if (uiState is UiState.Error) {
                    EmptyView(modifier = Modifier.fillMaxHeight(), message = uiState.errorMessage)
                }
                if (uiState is UiState.Success) {
                    FavouriteSection(uiState.state.isFavouriteView) {
                        events(it)
                    }
                    AppBarDivider()
                    AnimatedVisibility(visible = true) {
                        MatchesListScreenContent(
                            uiState.state.isFavouriteView,
                            uiState.state.scrollToSelectedDay,
                            uiState.state.selectedDay,
                            uiState.state.daysList,
                            uiState.state.matches,
                            imageLoader
                        ) {
                            events(it)
                        }
                    }
                }
            }

        }
    }
}








