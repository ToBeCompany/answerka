package com.arbonik.answerka.database.realm

import com.arbonik.answerka.database.data.AskDb
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun realm(): Realm {
    val configuration = RealmConfiguration.create(schema = setOf(AskDb::class))
    val realm = Realm.open(configuration)
    return realm
}

