package com.matches.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TeamName(teamName: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        text = teamName,
        style = MaterialTheme.typography.caption.copy(
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
    )
}