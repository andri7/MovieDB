package com.atf.moviedb.core.state

sealed interface UiEvent {

    data class Message(val text: String) : UiEvent

    data object Unauthorized : UiEvent
}