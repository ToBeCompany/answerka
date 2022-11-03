package com.arbonik.answerka.database

import com.arbonik.answerka.database.data.AskDb
import com.arbonik.answerka.database.data.TaskDb
import com.arbonik.answerka.entity.Ask
import com.arbonik.answerka.entity.Task
import io.realm.kotlin.ext.query


class GameRepository(
    private val realmDatabase: RealmDatabase,
) {

    fun getTasks() = realmDatabase.realm.query<TaskDb>().find()
        .map { t ->
            Task(t.text, t.is18)
        }

    fun getAsks() = realmDatabase.realm.query<AskDb>().find()
        .map { t ->
            Ask(t.text, t.is18)
        }
}

