package com.matches.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.matches.presentation.ui.MatchesListEvents
import com.mhmd.components.GrayTransparentColor
import com.mhmd.components.R
import com.mhmd.core.util.TestTags.FAVOURITE_SWITCHER


@Composable
fun FavouriteSection(isFavouriteView: Boolean, events: (MatchesListEvents) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.mark_fixture_favorite),
            style = MaterialTheme.typography.overline.copy(color = MaterialTheme.colors.onSurface)
        )
        Switch(
            modifier = Modifier.clearAndSetSemantics {
                testTag = FAVOURITE_SWITCHER
                role = Role.Switch
                toggleableState = if (isFavouriteView) {
                    ToggleableState.On
                } else {
                    ToggleableState.Off
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.onBackground,
                uncheckedThumbColor = MaterialTheme.colors.onSurface,
                checkedTrackColor = MaterialTheme.colors.onBackground,
                uncheckedTrackColor = GrayTransparentColor,
            ),
            checked = isFavouriteView, onCheckedChange = {
                events(MatchesListEvents.OnChangeFavouriteView(it))
            })
    }
}