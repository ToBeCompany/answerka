package com.arbonik.answerka.di

import com.arbonik.answerka.database.InitData
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<InitData>{ AndroidInitData(androidContext()) }
}