package com.favouriteMatches.presentation.model

import com.favouriteMatches.presentation.FavouriteMatchesEvents
import com.matches.presentation.ui.MatchesListEvents

fun MatchesListEvents.toFavouriteMatchesEvent(): FavouriteMatchesEvents? {
    return when (this) {
        MatchesListEvents.DisableScrollingToSelectedDay -> FavouriteMatchesEvents.DisableScrollingToSelectedDay
        MatchesListEvents.GetMatches -> FavouriteMatchesEvents.GetMatches
        is MatchesListEvents.OnChangeFavouriteView -> null
        is MatchesListEvents.OnMakeFavourite -> FavouriteMatchesEvents.OnMakeUnFavourite(this.uiMatchModel)
        MatchesListEvents.OnRemoveHeadFromQueue -> FavouriteMatchesEvents.OnRemoveHeadFromQueue
    }
}