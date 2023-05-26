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
import com.favouriteMatches.presentation.FavouriteMatchesScreen
import com.favouriteMatches.presentation.FavouriteMatchesViewModel
import com.google.accompanist.navigation.animation.composable


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.addFavouriteList(
    imageLoader: ImageLoader,
    navController: NavController,
    width: Int,
) {
    composable(
        route = Screen.FavouriteList.route,
        arguments = Screen.FavouriteList.arguments,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {

        val viewModel: FavouriteMatchesViewModel = hiltViewModel()
        FavouriteMatchesScreen(
            uiState = viewModel.state.value,
            events = viewModel::onTriggerEvent,
            navigateToBackScreen = {
                navController.navigateUp()
            },
            imageLoader = imageLoader,
        )
    }
}