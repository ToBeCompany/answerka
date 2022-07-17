package com.arbonik.answerka.database.data

import io.realm.kotlin.types.RealmObject

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