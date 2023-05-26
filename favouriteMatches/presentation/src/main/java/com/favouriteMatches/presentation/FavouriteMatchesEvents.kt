package com.favouriteMatches.presentation

import com.matches.presentation.ui.model.UiMatchModel

sealed class FavouriteMatchesEvents {
    object GetMatches : FavouriteMatchesEvents()
    object OnRemoveHeadFromQueue : FavouriteMatchesEvents()
    object DisableScrollingToSelectedDay : FavouriteMatchesEvents()
    data class OnMakeUnFavourite(val uiMatchModel: UiMatchModel) : FavouriteMatchesEvents()
}
