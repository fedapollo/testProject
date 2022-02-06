package com.example.data.api

import com.example.data.AppDataInitializer
import com.example.data.controller.remote.Connectivity
import com.example.data.repository.NotConnectedException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder : KoinComponent {
    private val httpClient: OkHttpClient
        get() {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val connectivityInterceptor = Interceptor { chain ->
                if (Connectivity.isConnected(AppDataInitializer.app).not()) {
                    throw NotConnectedException()
                }
                chain.proceed(chain.request())
            }
            val noContentInterceptor = Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val responseBuilder = response.newBuilder()
                if (response.code == 204) {
                    responseBuilder.body("".toResponseBody()).build()
                    responseBuilder.addHeader("Content-Length", "0")
                    responseBuilder.code(200)
                }
                responseBuilder.build()
            }

            val builder = OkHttpClient.Builder()
            builder.apply {
                addInterceptor(connectivityInterceptor)
                addInterceptor(logInterceptor)
                addInterceptor(noContentInterceptor)
                readTimeout(30, TimeUnit.SECONDS)
                connectTimeout(30, TimeUnit.SECONDS)
            }
            return builder.build()
        }

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://api.github.com/search/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    fun <T> createService(
        serviceClass: Class<T>
    ): T {
        return retrofit
            .create(serviceClass)
    }
}