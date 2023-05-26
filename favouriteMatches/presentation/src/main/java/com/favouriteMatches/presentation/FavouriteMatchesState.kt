package com.favouriteMatches.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import com.matches.presentation.ui.model.UiMatchModel
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class FavouriteMatchesState(
    val matches: Map<LocalDate, List<UiMatchModel>> = emptyMap(),
    val daysList: List<LocalDate> = emptyList(),
    val selectedDay: LocalDate = LocalDate.now(),
    val scrollToSelectedDay: Boolean = true,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
