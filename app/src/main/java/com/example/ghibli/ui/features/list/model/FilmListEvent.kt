package com.example.ghibli.ui.features.list.model

sealed class FilmListEvent {
    data class OnNavigateToDetailPage(val filmId: String) : FilmListEvent()
    data object OnReloadData : FilmListEvent()
}
