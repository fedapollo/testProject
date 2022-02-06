package com.example.testproject.di

import com.example.data.api.RetrofitBuilder
import com.example.data.repository.GitHubRepository
import com.example.data.repository.GitHubRepositoryImpl
import com.example.testproject.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { MainViewModel(get()) }
}

val repositories = module {
    single<GitHubRepository> { GitHubRepositoryImpl(get()) }
}

val commons = module {
    single { RetrofitBuilder() }
}