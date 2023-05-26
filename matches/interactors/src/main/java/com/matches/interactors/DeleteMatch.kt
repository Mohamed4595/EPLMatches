package com.matches.interactors

import com.matches.data.cach.MatchDao
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteMatch(private val service: MatchDao) {

    fun execute(id: Long): Flow<DataState<Boolean>> =
        flow {
            try {
                val result = service.deleteMatch(id.toInt())
                emit(DataState.Success(result >= 1))
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





