package com.arbonik.answerka.di

import com.arbonik.answerka.database.GameRepository
import com.arbonik.answerka.database.RealmDatabase
import com.arbonik.answerka.viewmodels.GameViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin(
    modify : (KoinApplication) -> Unit
) {
    startKoin {
        modules(appModule())
        modify(this)
    }
}

fun appModule() = listOf(platformModule, commonModule)

val commonModule = module {
    singleOf(::RealmDatabase)
    singleOf(::GameRepository)
    singleOf(::GameViewModel)
}

expect val platformModule : Module