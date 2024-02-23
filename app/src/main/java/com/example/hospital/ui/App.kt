package com.example.hospital.ui

import android.app.Application
import com.example.hospital.utils.MySharedPreferences
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreferences.init(this)
    }
}