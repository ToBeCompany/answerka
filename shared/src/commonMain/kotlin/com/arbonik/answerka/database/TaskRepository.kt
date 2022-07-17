package com.arbonik.answerka.database

import com.arbonik.answerka.database.data.AskDb
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class TaskRepository(
    private val database: Realm
) {

    suspend fun addRow(askDb: AskDb){
        database.write {
            copyToRealm(askDb)
        }
    }
    fun getAsks() = database.query<AskDb>().find()

    // задание для одного игрока
    fun getSoloTasks(): String{
        return ""
    }

    // задание для нескольких игроков
    fun getGroupTasks(): String{
        return ""
    }
}
