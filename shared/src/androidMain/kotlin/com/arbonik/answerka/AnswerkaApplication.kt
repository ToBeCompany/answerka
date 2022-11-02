package com.arbonik.answerka

import android.app.Application
import com.arbonik.answerka.di.initKoin
import org.koin.android.ext.koin.androidContext

class AnswerkaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { koinApplication ->
            koinApplication.androidContext(this)
        }
    }
}