package com.matches.presentation.ui.model

import com.matches.domain.MatchModel

data class UiMatchModel(
    val matchModel: MatchModel,
    val isFavourite:Boolean
)
