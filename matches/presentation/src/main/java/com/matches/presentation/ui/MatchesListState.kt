package com.matches.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.matches.presentation.ui.model.UiMatchModel
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class MatchesListState(
    val matches: Map<LocalDate, List<UiMatchModel>> = emptyMap(),
    val daysList: List<LocalDate> = emptyList(),
    val selectedDay: LocalDate = LocalDate.now(),
    val scrollToSelectedDay: Boolean = true,
    val isFavouriteView: Boolean = false,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
