package com.example.ghibli.ui.features.detail

import androidx.lifecycle.viewModelScope
import com.example.ghibli.data.helper.ApiResult
import com.example.ghibli.data.model.Film
import com.example.ghibli.data.repository.FilmRepository
import com.example.ghibli.ui.features.detail.model.FilmDetailEvent
import com.example.ghibli.ui.helper.BaseViewModel
import com.example.ghibli.ui.helper.UiState
import com.example.ghibli.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val repository: FilmRepository,
    private val navigator: Navigator,
) : BaseViewModel<FilmDetailEvent>() {

    private val _state = MutableStateFlow<UiState<Film>>(UiState.Loading)
    val state: StateFlow<UiState<Film>> = _state.asStateFlow()

    fun getFilmDetail(id: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            when (val result = repository.getFilmDetail(id)) {
                is ApiResult.Success -> {
                    _state.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
            }
        }
    }

    override fun onEvent(event: FilmDetailEvent) {
        when (event) {
            FilmDetailEvent.OnNavigateBack -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            is FilmDetailEvent.OnReloadData -> {
                getFilmDetail(event.filmId)
            }
        }
    }
}
