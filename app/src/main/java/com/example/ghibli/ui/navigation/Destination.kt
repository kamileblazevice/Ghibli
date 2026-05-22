package com.example.ghibli.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object AppGraph : Destination

    @Serializable
    data object FilmListScreen : Destination

    @Serializable
    data class FilmDetailScreen(val id: String) : Destination

}
