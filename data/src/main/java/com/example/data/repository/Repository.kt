package com.example.data.repository

interface Repository {

    fun <T> handleResult(
        result: Result<T>,
        onFailure: (e: Throwable?) -> Unit = {}
    ): T? {
        return if (result.isFailure) {
            onFailure(result.exceptionOrNull())
            null
        } else {
            result.getOrNull()
        }
    }

}