package com.matches.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.matches.presentation.ui.MatchesListEvents
import com.matches.presentation.ui.model.UiMatchModel
import com.mhmd.components.EmptyView
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchesListScreenContent(
    isFavouriteView: Boolean,
    scrollToSelectedDay: Boolean,
    selectedDay: LocalDate,
    daysList: List<LocalDate>,
    daysMap: Map<LocalDate, List<UiMatchModel>>,
    imageLoader: ImageLoader,
    events: (MatchesListEvents) -> Unit,
) {
    if (daysList.isEmpty())
        EmptyView(modifier = Modifier.fillMaxSize())
    else {
        val state = rememberLazyListState()
        val scope = rememberCoroutineScope()

        Column {

            LazyColumn(
                state = state,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(daysList) {
                    DayTitleContent(it, daysMap[it]?.size ?: 0)
                    MatchContent(
                        isFavouriteView,
                        daysMap[it] ?: emptyList(),
                        imageLoader
                    ) { matchEvent ->
                        events(matchEvent)
                    }
                }

            }
            if (scrollToSelectedDay)
                LaunchedEffect(key1 = true) {
                    scope.launch {
                        val index = daysList.indexOf(selectedDay)
                        if (index > 0) {
                            state.animateScrollToItem(index)
                        }
                        events(MatchesListEvents.DisableScrollingToSelectedDay)
                    }
                }
        }
    }
}

