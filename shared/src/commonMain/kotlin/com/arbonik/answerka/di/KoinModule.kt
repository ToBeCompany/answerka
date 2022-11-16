package com.arbonik.answerka.di

import com.arbonik.answerka.database.GameRepository
import com.arbonik.answerka.database.GameRepositoryRealm
import com.arbonik.answerka.database.RealmDatabase
import com.arbonik.answerka.viewmodels.GameViewModel
import com.arbonik.answerka.viewmodels.PaymentViewModel
import com.arbonik.answerka.core.PromoCodeManager
import com.arbonik.answerka.viewmodels.SettingViewModel
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
    single<GameRepository> { GameRepositoryRealm(get()) }
    singleOf(::GameViewModel)
    singleOf(::PromoCodeManager)
    singleOf(::PaymentViewModel)
    singleOf(::SettingViewModel)
}

expect val platformModule : Module