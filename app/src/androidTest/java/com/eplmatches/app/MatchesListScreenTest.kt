package com.eplmatches.app

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onLast
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import coil.ImageLoader
import com.eplmatches.app.coil.FakeImageLoader
import com.eplmatches.app.di.AppModule
import com.eplmatches.app.ui.MainActivity
import com.eplmatches.app.ui.navigation.Screen
import com.eplmatches.app.ui.theme.EPLTheme
import com.matches.presentation.ui.MatchesListScreen
import com.matches.presentation.ui.MatchesListState
import com.matches.presentation.ui.toMatchesMap
import com.mhmd.core.data.MatchesDataValid
import com.mhmd.core.domain.UiState
import com.mhmd.core.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
@UninstallModules(AppModule::class)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
class MatchesListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader: ImageLoader = FakeImageLoader.build(context)
    private val matchesData = serializeMatchesData(MatchesDataValid.data)
    private val checked = mutableStateOf(false)

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            EPLTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.MatchesList.route
                ) {
                    composable(route = Screen.MatchesList.route) {
                        val state = remember {
                            val map = matchesData?.toMatchesMap() ?: emptyMap()
                            val selectedDay =
                                if (map[LocalDate.now()].isNullOrEmpty()) LocalDate.now()
                                    .plusDays(1)
                                else LocalDate.now()
                            UiState.Success(
                                MatchesListState(
                                    matches = map,
                                    daysList = map.keys.toList().asReversed(),
                                    selectedDay = selectedDay,
                                    isFavouriteView = checked.value
                                )
                            )
                        }
                        MatchesListScreen(
                            uiState = state,
                            events = {

                            },
                            navigateToFavouriteScreen = { },
                            imageLoader = imageLoader
                        )
                    }
                }
            }
        }
    }

    @Test
    fun matches_list_favourite_button_is_exist() {
        checked.value = true
        composeRule.onAllNodesWithTag(TestTags.FAVOURITE_LIST_BUTTON).onLast().assertExists()
    }

    @Test
    fun matches_list_favourite_button_is_not_exist() {
        checked.value = false
        composeRule.onAllNodesWithTag(TestTags.FAVOURITE_LIST_BUTTON).onLast().assertDoesNotExist()
    }
}