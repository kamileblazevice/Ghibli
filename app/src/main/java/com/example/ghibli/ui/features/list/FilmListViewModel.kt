package com.example.ghibli.ui.features.list

import androidx.lifecycle.viewModelScope
import com.example.ghibli.data.helper.ApiResult
import com.example.ghibli.data.model.Film
import com.example.ghibli.data.repository.FilmRepository
import com.example.ghibli.ui.features.list.model.FilmListEvent
import com.example.ghibli.ui.helper.BaseViewModel
import com.example.ghibli.ui.helper.UiState
import com.example.ghibli.ui.navigation.Destination
import com.example.ghibli.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository,
    private val navigator: Navigator,
) : BaseViewModel<FilmListEvent>() {

    private val _state = MutableStateFlow<UiState<List<Film>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Film>>> = _state.asStateFlow()

    init {
        loadFilms()
    }

    private fun loadFilms() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            when (val result = repository.getFilms()) {
                is ApiResult.Success -> {
                    _state.value = UiState.Success(result.data)
                }

                is ApiResult.Error -> {
                    _state.value = UiState.Error(result.message)
                }
            }
        }
    }

    override fun onEvent(event: FilmListEvent) {
        when (event) {
            is FilmListEvent.OnNavigateToDetailPage -> {
                viewModelScope.launch {
                    navigator.navigate(
                        destination = Destination.FilmDetailScreen(event.filmId),
                    )
                }
            }

            FilmListEvent.OnReloadData -> loadFilms()
        }
    }
}
