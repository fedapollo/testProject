package com.example.data.repository

import com.example.data.api.GitHubAPI
import com.example.data.api.RetrofitBuilder
import com.example.data.entity.GitError
import com.example.data.entity.GitRepositoriesResponseModel

interface GitHubRepository : Repository {

    suspend fun getRepositories(page: Int): Result<GitRepositoriesResponseModel>
}

open class GitHubRepositoryImpl(
    private val retrofit: RetrofitBuilder
) : BaseRepository(), GitHubRepository {

    override suspend fun getRepositories(page: Int): Result<GitRepositoriesResponseModel> {
        return handleResponse(errorBodyType = GitError::class.java) {
            retrofit
                .createService(GitHubAPI::class.java)
                .getRepositories(page)
        }
    }
}