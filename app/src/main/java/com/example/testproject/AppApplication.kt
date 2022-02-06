package com.example.testproject

import android.app.Application
import android.content.Context
import com.example.data.AppDataInitializer
import com.example.testproject.di.commons
import com.example.testproject.di.repositories
import com.example.testproject.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    companion object {
        lateinit var sInstance: AppApplication
    }


    override fun onCreate() {
        super.onCreate()

        sInstance = this
        AppDataInitializer().initialize(this)

        val appModules = listOf(
            viewModels,
            repositories,
            commons
        )

        startKoin {
            androidContext(sInstance)
            modules(appModules)
        }
    }

    fun getAppContext(): Context {
        return sInstance
    }
}