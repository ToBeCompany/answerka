package com.arbonik.answerka.database

import io.realm.kotlin.MutableRealm

interface InitData {
    fun loadDataFromAsset(realm: MutableRealm)
}