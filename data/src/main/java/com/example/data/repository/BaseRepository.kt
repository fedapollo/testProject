package com.example.data.repository

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    protected suspend fun <T, R> handleResponse(
        errorBodyType: Class<R>? = null,
        call: suspend () -> Response<T>
    ): Result<T> {
        return withContext<Result<T>>(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val parsedBody = response.body() ?: throw NullPointerException()
                    Result.success(parsedBody)
                } else {
                    val errorBody = try {
                        Gson().fromJson(response.errorBody()?.string(), errorBodyType)
                    } catch (e: JsonSyntaxException) {
                        null
                    }
                    when (response.code()) {
                        400 -> throw BadRequest(errorBody = errorBody)
                        401 -> throw UnauthorizedException(errorBody = errorBody)
                        404 -> throw NotFoundException(errorBody = errorBody)
                        422 -> throw UnprocessableEntity(errorBody = errorBody)
                        503 -> throw MaintenanceException(errorBody = errorBody)
                        else -> throw OtherException(errorBody = errorBody)
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}

class NotConnectedException : IOException()
class BadRequest(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class UnauthorizedException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class NotFoundException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class UnprocessableEntity(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class OtherException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
class MaintenanceException(msg: String? = null, val errorBody: Any? = null) : Exception(msg)
