package com.example.testproject.ui.main

import com.example.data.entity.GitRepository
import com.example.testproject.common.SingleLiveEvent

class MainModel {

    val stateOb = SingleLiveEvent<MainState>()

    var pageNumber = 1
    val repositories = mutableListOf<GitRepository>()

    sealed class MainState {
        data class Loading(val isLoading: Boolean) : MainState()
        object DataLoaded : MainState()
        object Error : MainState()
        object NotConnected : MainState()
    }
}