package com.eplmatches.app.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument>) {


    object Splash : Screen(
        route = "splash",
        arguments = emptyList()
    )

    object MatchesList : Screen(
        route = "matchesList",
        arguments = emptyList()
    )

    object FavouriteList : Screen(
        route = "favouriteList",
        arguments = emptyList()
    )

}

