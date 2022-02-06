package com.example.data.api

import com.example.data.entity.GitRepositoriesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubAPI {

    @GET("repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositories(
        @Query("page") page: Int
    ): Response<GitRepositoriesResponseModel>
}