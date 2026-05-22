package com.example.ghibli.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val director: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
)
