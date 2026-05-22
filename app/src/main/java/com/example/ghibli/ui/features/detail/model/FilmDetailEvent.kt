package com.example.ghibli.ui.features.detail.model

sealed class FilmDetailEvent {
    data object OnNavigateBack : FilmDetailEvent()
    data class OnReloadData(val filmId: String) : FilmDetailEvent()
}
