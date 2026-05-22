package com.example.ghibli.data.network

import com.example.ghibli.data.model.Film
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("films")
    suspend fun getFilms(): Response<List<Film>>

    @GET("films/{id}")
    suspend fun getFilmDetail(@Path("id") id: String): Response<Film>
}
