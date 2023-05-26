package com.matches.presentation.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.matches.domain.ScoreModel


@Composable
fun MatchStatus(status: String, score: ScoreModel?) {
    Text(
        text = status,
        style = if (score == null)
            MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground)
        else
            MaterialTheme.typography.overline.copy(color = MaterialTheme.colors.onSurface)
    )
}