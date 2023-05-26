package com.matches.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.matches.domain.ScoreModel

@Composable
fun ScoreContent(score: ScoreModel?) {
    if (score != null)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (score.fullTime != null)
                Text(
                    text = score.fullTime!!.home.toString() + " : " + score.fullTime!!.away.toString(),
                    style =
                    MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground)
                )
            if (score.halfTime != null)
                Text(
                    text = score.halfTime!!.home.toString() + " : " + score.halfTime!!.away.toString(),
                    style = if (score.fullTime == null)
                        MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground)
                    else
                        MaterialTheme.typography.overline.copy(color = MaterialTheme.colors.onSurface)
                )
        }
}