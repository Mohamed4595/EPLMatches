package com.splash.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mhmd.components.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navigateToMatchesScreen: () -> Unit,
) {
    val scope = rememberCoroutineScope()


    val listColors =
        listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listColors))
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            val state = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }
            AnimatedVisibility(
                visibleState = state,
                enter = fadeIn(
                    animationSpec = repeatable(
                        2, animation = tween(durationMillis = 2000),
                        repeatMode = RepeatMode.Reverse
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = FastOutLinearInEasing
                    )
                )
            ) {
                Image(
                    modifier = Modifier.size(150.dp).clip(
                        CircleShape).background(MaterialTheme.colors.onPrimary).padding(24.dp),
                    painter = painterResource(id = R.drawable.ic_epl),
                    contentDescription = null,
                )
            }

        }
    }
    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(3500)
            navigateToMatchesScreen()
        }
    }
}








