package com.example.data

import android.app.Application

class AppDataInitializer {

    fun initialize(app: Application) {
        AppDataInitializer.app = app
    }

    companion object {
        lateinit var app: Application
    }
}