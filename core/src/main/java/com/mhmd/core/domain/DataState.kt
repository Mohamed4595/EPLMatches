package com.mhmd.core.domain

sealed class DataState<T> {

    data class Error<T>(
        val uiComponent: UIComponent
    ): DataState<T>()

    data class Success<T>(
        val data: T? = null
    ): DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
}