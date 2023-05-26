package com.matches.interactors

import com.matches.data.network.MatchesService
import com.matches.domain.MatchModel
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMatches(private val service: MatchesService) {

    fun execute(): Flow<DataState<List<MatchModel>>> =
        flow {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            try {
                when (val result = service.getMatches()) {
                    is ApiResponse.Fail -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(
                            DataState.Error(
                                uiComponent = UIComponent.Dialog(
                                    title = "Network Data Error",
                                    description = result.response.message.toString()
                                )
                            )
                        )


                    }

                    is ApiResponse.Success -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(DataState.Success(result.data))
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                emit(
                    DataState.Error(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
            }


        }
}




