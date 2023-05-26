package com.mhmd.core.domain

sealed class UiState<T> {

    data class Error<T>(
        val errorMessage: String,
        val state: T
    ): UiState<T>()

    data class Success<T>(
        val state: T
    ): UiState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle,
        val state: T
    ): UiState<T>()
}