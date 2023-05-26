package com.matches.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.matches.presentation.ui.MatchesListEvents
import com.matches.presentation.ui.model.UiMatchModel
import com.mhmd.components.AppBarDivider
import com.mhmd.components.IconButton


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchContent(
    isFavouriteView: Boolean,
    matchModels: List<UiMatchModel>,
    imageLoader: ImageLoader,
    events: (MatchesListEvents) -> Unit
) {
    matchModels.forEach {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TeamContent(Modifier.fillMaxWidth(.33f), it.matchModel.homeTeam, imageLoader)
            Column(
                modifier = Modifier.fillMaxWidth(.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MatchStatus(it.matchModel.status, it.matchModel.score)
                ScoreContent(it.matchModel.score)
                MatchTime(it.matchModel.date, it.matchModel.score)
                if (isFavouriteView)
                    IconButton(iconRes = if (it.isFavourite) com.mhmd.components.R.drawable.ic_heart_filled else com.mhmd.components.R.drawable.ic_heart_outlined) {
                        events(MatchesListEvents.OnMakeFavourite(it))
                    }
            }
            TeamContent(Modifier.fillMaxWidth(), it.matchModel.awayTeam, imageLoader)
        }
        AppBarDivider()
    }
}




