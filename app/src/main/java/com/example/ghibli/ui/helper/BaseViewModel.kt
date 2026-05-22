package com.example.ghibli.ui.helper

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<UiEvent> : ViewModel() {

    abstract fun onEvent(event: UiEvent)
}
