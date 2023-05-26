package com.eplmatches.app.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.splash.presentation.SplashScreen


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.addSplash(
    navController: NavController,
    width: Int,
) {
    composable(
        route = Screen.Splash.route,
        arguments = Screen.Splash.arguments,
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
        SplashScreen(
            navigateToMatchesScreen = {
                navController.navigate(Screen.MatchesList.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            },
        )
    }
}