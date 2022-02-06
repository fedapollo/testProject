package com.example.testproject.ui.main

import androidx.lifecycle.viewModelScope
import com.example.data.repository.GitHubRepository
import com.example.data.repository.NotConnectedException
import com.example.testproject.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val gitRepository: GitHubRepository
) : BaseViewModel() {

    val model = MainModel()

    override fun initialize() {
        if (model.pageNumber == 1) {
            getRepositories()
        }
    }

    fun getRepositories() {
        viewModelScope.launch {
            model.stateOb.value = MainModel.MainState.Loading(true)
            val response = gitRepository.getRepositories(model.pageNumber)
            val responseModel = handleResponse(response, ::handleError)
            responseModel?.let { gitRepositoriesResponseModel ->
                model.pageNumber++
                model.repositories.addAll(gitRepositoriesResponseModel.repositories)
                model.stateOb.value = MainModel.MainState.DataLoaded
                model.stateOb.value = MainModel.MainState.Loading(false)
            }
        }
    }

    private fun handleError(error: Throwable?) {
        model.stateOb.value = MainModel.MainState.Loading(false)
        when (error) {
            is NotConnectedException -> model.stateOb.value = MainModel.MainState.NotConnected
            else -> model.stateOb.value = MainModel.MainState.Error
        }
    }
}