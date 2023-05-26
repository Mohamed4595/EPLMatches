package com.matches.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.matches.domain.TeamModel

@Composable
fun TeamContent(
    modifier: Modifier = Modifier,
    homeTeam: TeamModel,
    imageLoader: ImageLoader
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamImage(imageLoader, homeTeam.teamImage)
        TeamName(homeTeam.teamName)
    }
}