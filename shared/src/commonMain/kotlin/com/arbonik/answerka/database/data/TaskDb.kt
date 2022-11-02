package com.arbonik.answerka.database.data

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TaskDb(
    var text: String = "",
    var is18: Boolean = false
) : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.create()
    constructor() : this("", false)
}
