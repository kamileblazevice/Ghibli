package com.example.ghibli.data.repository

import com.example.ghibli.data.helper.ApiResult
import com.example.ghibli.data.helper.safeApiCall
import com.example.ghibli.data.model.Film
import com.example.ghibli.data.network.ApiService
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getFilms(): ApiResult<List<Film>> {
        return safeApiCall {
            api.getFilms()
        }
    }

    suspend fun getFilmDetail(id: String): ApiResult<Film> {
        return safeApiCall {
            api.getFilmDetail(id)
        }
    }
}
