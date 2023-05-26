package com.matches.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.matches.domain.ScoreModel
import com.mhmd.core.util.getTranslatedTime
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchTime(date: LocalDateTime?, score: ScoreModel?) {
    if (score?.fullTime == null && score?.halfTime == null && date != null)
        Text(
            text = getTranslatedTime(date.toLocalTime()),
            style =
            MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground)
        )
}