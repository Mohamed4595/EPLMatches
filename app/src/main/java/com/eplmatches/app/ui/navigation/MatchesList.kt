package com.eplmatches.app.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.composable
import com.matches.presentation.ui.MatchesListScreen
import com.matches.presentation.ui.MatchesListViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.addMatchesList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
) {
    composable(
        route = Screen.MatchesList.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel: MatchesListViewModel = hiltViewModel()
        MatchesListScreen(
            uiState = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            navigateToFavouriteScreen = {
                navController.navigate(Screen.FavouriteList.route)
            },
            imageLoader = imageLoader,
        )
    }
}
