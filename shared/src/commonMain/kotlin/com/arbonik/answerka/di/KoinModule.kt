package com.arbonik.answerka.di

import com.arbonik.answerka.database.TaskRepository
import com.arbonik.answerka.database.realm.realm
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}

fun appModule() = listOf(commonModule, platformModule)

val commonModule = module {
    singleOf(::realm)
    singleOf(::TaskRepository)
}

val platformModule = module {
//    singleOf()
}