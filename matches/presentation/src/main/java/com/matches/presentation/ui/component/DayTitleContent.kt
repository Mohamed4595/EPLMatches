package com.matches.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mhmd.components.R
import com.mhmd.core.util.getTranslatedDateWithMonthName
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayTitleContent(it: LocalDate, matchesCount: Int) {
    val currentDate = LocalDate.now()
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = when (it) {
                currentDate -> stringResource(id = R.string.today)
                currentDate.plusDays(1) -> stringResource(id = R.string.tomorrow)
                currentDate.minusDays(1) -> stringResource(id = R.string.yesterday)
                else -> getTranslatedDateWithMonthName(it)
            },
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
        )
        Text(
            text = stringResource(id = R.string.matches_count) + matchesCount,
            style = MaterialTheme.typography.overline.copy(color = MaterialTheme.colors.onSurface)
        )
    }

}
