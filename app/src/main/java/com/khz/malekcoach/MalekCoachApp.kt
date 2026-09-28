package com.khz.malekcoach

import android.app.Application
import com.khz.malekcoach.core.di.AppContainer

class MalekCoachApp : Application() {

    // ظرف اصلی وابستگی‌های پروژه
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)


    }
}