package com.matches.interactors

import com.matches.data.cach.MatchDao
import com.matches.data.cach.model.toMatchEntity
import com.matches.domain.MatchModel
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertMatch(private val service: MatchDao) {

    fun execute(matchModel: MatchModel): Flow<DataState<Boolean?>> =
        flow {
            try {
                val result = service.insertMatch(matchModel.toMatchEntity())
                emit(DataState.Success(result >= 1L))


            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
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





