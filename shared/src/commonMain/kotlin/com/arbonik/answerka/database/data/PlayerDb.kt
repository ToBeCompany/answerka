package com.arbonik.answerka.database.data

import com.arbonik.answerka.entity.Player
import io.realm.kotlin.types.RealmObject


class PlayerDb(
    var name :String = ""
) : RealmObject {
    constructor() : this("")
    fun fromDb() = Player(name)
}