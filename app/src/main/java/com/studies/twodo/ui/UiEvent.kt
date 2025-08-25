package com.studies.twodo.ui

sealed interface UiEvent {
    data class showSnackbar(val message: String) : UiEvent
    data class navigate<T : Any>(val route: T) : UiEvent
    data object goBack : UiEvent
}