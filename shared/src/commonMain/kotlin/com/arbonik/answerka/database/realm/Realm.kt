package com.arbonik.answerka.database.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmObject

class RealmDatabase {
    //    private val realm: Realm by lazy {
    fun realm() {
        val configuration = RealmConfiguration.create(schema = setOf(AskDb::class))
        val realm = Realm.open(configuration)
    }
//        }
}


class AskDb : RealmObject {
    var id: Int = 0
    var age: Int = 0
    var keyWords: List<String> = emptyList()
    var target: TaskTarget = TaskTarget.ANY
}

enum class TaskTarget {
    ANY,
    SOLO,
    PAIR,
    GROUP
}