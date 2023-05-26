package com.matches.interactors

import com.matches.data.cach.MatchDao
import com.matches.data.cach.model.toMatchModel
import com.matches.domain.MatchModel
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetLocalMatches(private val service: MatchDao) {

    fun execute(): Flow<DataState<List<MatchModel>>> =
        flow {
            try {
                emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
                val result = service.getMatches()
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                result.collect {
                    emit(DataState.Success(it.map { entity -> entity.toMatchModel() }))
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





