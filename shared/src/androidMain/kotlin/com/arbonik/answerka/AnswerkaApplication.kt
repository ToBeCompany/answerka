package com.arbonik.answerka

import android.app.Application
import com.arbonik.answerka.di.initKoin

class AnswerkaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}