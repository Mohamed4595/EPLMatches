package com.matches.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matches.domain.MatchModel
import com.matches.interactors.DeleteMatch
import com.matches.interactors.GetLocalMatches
import com.matches.interactors.GetRemoteMatches
import com.matches.interactors.InsertMatch
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
class MatchesListViewModel
@Inject
constructor(
    private val remoteMatches: GetRemoteMatches,
    private val insertMatch: InsertMatch,
    private val getLocalMatches: GetLocalMatches,
    private val deleteMatch: DeleteMatch
) : ViewModel() {

    val state: MutableState<UiState<MatchesListState>> = mutableStateOf(
        UiState.Loading(
            progressBarState = ProgressBarState.Loading,
            state = MatchesListState()
        )
    )

    init {
        onTriggerEvent(MatchesListEvents.GetMatches)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onTriggerEvent(event: MatchesListEvents) {
        when (event) {
            is MatchesListEvents.GetMatches -> {
                getMatchesList()
            }

            is MatchesListEvents.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            MatchesListEvents.DisableScrollingToSelectedDay -> {
                disableScrollingToSelectedDay()
            }

            is MatchesListEvents.OnChangeFavouriteView -> {
                onChangeFavouriteView(event.isFavouriteView)
            }

            is MatchesListEvents.OnMakeFavourite -> {
                onMakeFavourite(event.uiMatchModel)
            }
        }
    }

    private fun onMakeFavourite(uiMatchModel: UiMatchModel) {
        val currentState = getCurrentState()

        when (uiMatchModel.isFavourite) {
            true -> deleteMatch.execute(uiMatchModel.matchModel.id).onEach {
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

            false -> insertMatch.execute(uiMatchModel.matchModel).onEach {
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

    }

    private fun onChangeFavouriteView(favouriteView: Boolean) {
        val currentState = getCurrentState()
        state.value =
            UiState.Success(
                state = currentState.copy(
                    isFavouriteView = favouriteView
                )
            )
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
        val currentState = getCurrentState()
        remoteMatches.execute()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value = UiState.Loading(
                            progressBarState = dataState.progressBarState,
                            state = currentState
                        )
                    }

                    is DataState.Success -> {

                        val map = dataState.data?.toMatchesMap() ?: emptyMap()

                        val selectedDay =
                            if (map[LocalDate.now()].isNullOrEmpty()) LocalDate.now()
                                .plusDays(1)
                            else LocalDate.now()
                        state.value =
                            UiState.Success(
                                state = currentState.copy(
                                    matches = map,
                                    daysList = map.keys.toList().asReversed(),
                                    selectedDay = selectedDay
                                )
                            )
                        updateMatchesFavouriteState()
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

    private fun updateMatchesFavouriteState() {

        getLocalMatches.execute().onEach {
            val currentState = getCurrentState()
            val map = currentState.matches.toMutableMap()
            val localMatches = when (it) {
                is DataState.Error -> emptyList()
                is DataState.Loading -> emptyList()
                is DataState.Success -> it.data ?: emptyList()
            }
            map.keys.forEach { date ->
                val list = map[date]?.toMutableList()
                list?.forEach { match ->
                    val checkIsFound =
                        localMatches.find { localMatch ->
                            match.matchModel.id == localMatch.id
                        }
                    val newItem = UiMatchModel(
                        matchModel = match.matchModel,
                        isFavourite = checkIsFound != null
                    )

                    val index = list.indexOf(match)
                    list[index] = newItem

                }
                map[date] = list?.toList() ?: emptyList()
            }
            state.value =
                UiState.Success(
                    state = currentState.copy(
                        matches = map,
                    )
                )
        }.launchIn(viewModelScope)
    }

    private fun getCurrentState(): MatchesListState {
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

@RequiresApi(Build.VERSION_CODES.O)
fun List<MatchModel>?.toMatchesMap(): MutableMap<LocalDate, List<UiMatchModel>> {
    val map: MutableMap<LocalDate, List<UiMatchModel>> = mutableMapOf()

    this?.forEach { match ->
        val list = map[match.date?.toLocalDate()]
        match.date?.let { date ->
            val newItem = UiMatchModel(
                matchModel = match,
                isFavourite = false
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
    return map
}
