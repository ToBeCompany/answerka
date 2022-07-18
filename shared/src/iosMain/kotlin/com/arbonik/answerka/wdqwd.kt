package com.arbonik.answerka

import com.arbonik.answerka.di.appModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}