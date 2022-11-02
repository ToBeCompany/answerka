package com.arbonik.answerka.database

import com.arbonik.answerka.database.data.AskDb
import com.arbonik.answerka.database.data.PlayerDb
import com.arbonik.answerka.database.data.TaskDb
import com.arbonik.answerka.entity.Ask
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmDatabase(
    private val initData: InitData
) {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(
            schema = setOf(
                PlayerDb::class,
                TaskDb::class,
                AskDb::class
            )
        ).initialData {
            initData.loadDataFromAsset(this)
        }.build()

        Realm.open(configuration)
    }
}