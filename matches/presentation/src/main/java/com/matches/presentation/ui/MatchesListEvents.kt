package com.matches.presentation.ui

import com.matches.presentation.ui.model.UiMatchModel

sealed class MatchesListEvents {
    object GetMatches : MatchesListEvents()
    object OnRemoveHeadFromQueue : MatchesListEvents()
    object DisableScrollingToSelectedDay : MatchesListEvents()
    data class OnChangeFavouriteView(val isFavouriteView: Boolean) : MatchesListEvents()
    data class OnMakeFavourite(val uiMatchModel: UiMatchModel) : MatchesListEvents()
}
