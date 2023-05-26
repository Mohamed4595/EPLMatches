package com.eplmatches.app.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.ImageLoader
import com.eplmatches.app.R
import com.eplmatches.app.ui.navigation.Screen
import com.eplmatches.app.ui.navigation.addFavouriteList
import com.eplmatches.app.ui.navigation.addMatchesList
import com.eplmatches.app.ui.navigation.addSplash
import com.eplmatches.app.ui.theme.EPLTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // https://coil-kt.github.io/coil/getting_started/#image-loaders
    @Inject
    lateinit var imageLoader: ImageLoader

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContent {
            EPLTheme {
                BoxWithConstraints {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        builder = {
                            addSplash(
                                navController = navController,
                                width = constraints.maxWidth / 2
                            )
                            addMatchesList(
                                navController = navController,
                                imageLoader = imageLoader,
                                width = constraints.maxWidth / 2,
                            )
                            addFavouriteList(
                                imageLoader = imageLoader,
                                navController = navController,
                                width = constraints.maxWidth / 2,
                            )
                        }
                    )
                }
            }
        }
    }
}














