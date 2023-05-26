package com.favouriteMatches.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matches.interactors.DeleteMatch
import com.matches.interactors.GetLocalMatches
import com.matches.presentation.ui.model.UiMatchModel
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.mhmd.core.domain.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FavouriteMatchesViewModel
@Inject
constructor(
    private val getLocalMatches: GetLocalMatches,
    private val deleteMatch: DeleteMatch
) : ViewModel() {

    val state: MutableState<UiState<FavouriteMatchesState>> = mutableStateOf(
        UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = FavouriteMatchesState()
        )
    )

    init {
        onTriggerEvent(FavouriteMatchesEvents.GetMatches)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onTriggerEvent(event: FavouriteMatchesEvents) {
        when (event) {
            is FavouriteMatchesEvents.GetMatches -> {
                getMatchesList()
            }

            is FavouriteMatchesEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            FavouriteMatchesEvents.DisableScrollingToSelectedDay -> {
                disableScrollingToSelectedDay()
            }

            is FavouriteMatchesEvents.OnMakeUnFavourite -> {
                onMakeUnFavourite(event.uiMatchModel)
            }
        }
    }

    private fun onMakeUnFavourite(uiMatchModel: UiMatchModel) {
        deleteMatch.execute(uiMatchModel.matchModel.id).onEach {
            val currentState = getCurrentState()
            when (it) {
                is DataState.Error -> {
                    state.value = UiState.Error(
                        errorMessage = when (val uiComponent = it.uiComponent) {
                            is UIComponent.None -> uiComponent.message
                            is UIComponent.Dialog -> uiComponent.title + "\n" + uiComponent.description
                        },
                        state = currentState
                    )

                }

                else -> {}
            }
        }.launchIn(viewModelScope)


    }

    private fun disableScrollingToSelectedDay() {
        val currentState = getCurrentState()
        state.value =
            UiState.Success(
                state = currentState.copy(
                    scrollToSelectedDay = false
                )
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMatchesList() {
        getLocalMatches.execute()
            .onEach { dataState ->
                val currentState = getCurrentState()
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Loading(
                            progressBarState = dataState.progressBarState,
                            state = currentState
                        )
                    }

                    is DataState.Success -> {
                        val map: MutableMap<LocalDate, List<UiMatchModel>> = mutableMapOf()

                        dataState.data?.forEach { match ->
                            val list = map[match.date?.toLocalDate()]
                            match.date?.let { date ->
                                val newItem = UiMatchModel(
                                    matchModel = match,
                                    isFavourite = true
                                )
                                if (list == null) {
                                    map[date.toLocalDate()] = listOf(
                                        newItem
                                    )
                                } else {
                                    val newList = list.toMutableList()
                                    newList.add(newItem)

                                    match.date?.let { matchDate ->
                                        map[matchDate.toLocalDate()] = newList
                                    }
                                }

                            }
                        }
                        val selectedDay =
                            if (map[LocalDate.now()].isNullOrEmpty()) LocalDate.now()
                                .plusDays(1)
                            else LocalDate.now()
                        state.value =
                            UiState.Success(
                                state = currentState.copy(
                                    matches = map,
                                    daysList = map.keys.toList().sortedDescending(),
                                    selectedDay = selectedDay
                                )
                            )
                    }

                    is DataState.Error -> {
                        state.value = UiState.Error(
                            errorMessage = when (val uiComponent = dataState.uiComponent) {
                                is UIComponent.None -> uiComponent.message
                                is UIComponent.Dialog -> uiComponent.title + "\n" + uiComponent.description
                            },
                            state = currentState
                        )

                    }
                }
            }.launchIn(viewModelScope)
    }


    private fun getCurrentState(): FavouriteMatchesState {
        return when (val result = state.value) {
            is UiState.Error -> result.state
            is UiState.Loading -> result.state
            is UiState.Success -> result.state
        }
    }

    private fun removeHeadMessage() {
        val currentState = getCurrentState()
        val queue = currentState.errorQueue
        if (!queue.isEmpty()) {
            queue.remove()
            when (state.value) {
                is UiState.Error -> {
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Error(
                        errorMessage = "",
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Loading -> {
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Loading(
                        state = currentState.copy(errorQueue = queue)
                    )

                }

                is UiState.Success -> {
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = Queue(mutableListOf()))
                    )
                    state.value = UiState.Success(
                        state = currentState.copy(errorQueue = queue)
                    )
                }
            }
        }

    }
}